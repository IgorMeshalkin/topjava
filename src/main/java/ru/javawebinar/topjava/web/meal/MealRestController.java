package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private final int userId = authUserId();

    @Autowired
    private MealService service;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(userId), authUserCaloriesPerDay());
    }

    public List<MealTo> getByDate(LocalDate startDate, LocalDate endDate) {
        log.info("getByDate");
        return MealsUtil.getTos(service.getByDateFilter(userId, startDate, endDate), authUserCaloriesPerDay());
    }

    public List<MealTo> getByTime(LocalTime startTime, LocalTime endTime) {
        log.info("getByTime");
        return MealsUtil.getTos(service.getByTimeFilter(userId, startTime, endTime), authUserCaloriesPerDay());
    }

    public Meal create(MealTo mealTo) {
        log.info("create {}", mealTo);
        Meal meal = new Meal(mealTo.getId(), userId, LocalDateTime.now(), mealTo.getDescription(), mealTo.getCalories());
        checkNew(meal);
        service.create(meal, userId);
        return meal;
    }

    public void update(MealTo mealTo) {
        log.info("update {} with id={}", mealTo, mealTo.getId());
        service.update(service.get(mealTo.getId(), userId), mealTo.getId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }




}