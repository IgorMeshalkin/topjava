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
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    public final int userId = authUserId();

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

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        service.create(meal, userId);
        return meal;
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, meal.getId());
        service.update(meal, userId);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }




}