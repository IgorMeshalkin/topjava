package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private static final String ALL_MEALS = "meals.jsp";
    private static final String UPDATE = "update.jsp";
    private static final String CREATE = "create.jsp";

    private static final MealDao dao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        String forward = "";

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                int id = Integer.parseInt(request.getParameter("mealId"));
                dao.delete(id);
                forward = ALL_MEALS;
                request.setAttribute("mealsToList", dao.getAll());
            }
            if (action.equalsIgnoreCase("update")) {
                int id = Integer.parseInt(request.getParameter("mealId"));
                forward = UPDATE;
                Meal meal = dao.getById(id);
                request.setAttribute("updateMeal", meal);
            }
            if (action.equalsIgnoreCase("add")) {
                forward = CREATE;
            }
        } else {
            forward = ALL_MEALS;
            request.setAttribute("mealsToList", dao.getAll());
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        /*This is the id obtained from the jsp.
        It specifies which meal to edit.
        If it is 0, you need to create a new meal.*/
        int incomingId;
        try {
            incomingId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            incomingId = 0;
        }

        if (incomingId == 0) {
            //Here is the meal create and add logic
            LocalDateTime dateTime = LocalDateTime.now();
            String stringDateTime = request.getParameter("datetime");
            if (!stringDateTime.equalsIgnoreCase("")) {
                dateTime = LocalDateTime.parse(request.getParameter("datetime"));
            }

            String description = request.getParameter("description");

            int calories;
            try {
                calories = Integer.parseInt(request.getParameter("calories"));
            } catch (NumberFormatException e) {
                calories = 0;
            }

            dao.add(new Meal(dateTime, description, calories));

        } else {
            //Here is the meal update logic
            Meal meal = dao.getById(incomingId);

            String stringDateTime = request.getParameter("datetime");
            if (!stringDateTime.equalsIgnoreCase("")) {
                LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
                meal.setDateTime(dateTime);
            }

            meal.setDescription(request.getParameter("description"));
            meal.setCalories(Integer.parseInt(request.getParameter("calories")));
            dao.update(meal);
        }

        request.setAttribute("mealsToList", dao.getAll());
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
