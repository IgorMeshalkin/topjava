package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    private final MealService mealService;
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    JspMealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("")
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("All meals of user with id: {}", userId);
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        model.addAttribute("meal", mealService.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            mealService.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            mealService.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        mealService.delete(Integer.parseInt(request.getParameter("id")), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/between")
    public String getBetween(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<MealTo> result = MealsUtil.getFilteredTos(mealService.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay(),
                startTime, endTime);

        request.setAttribute("meals", result);
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
