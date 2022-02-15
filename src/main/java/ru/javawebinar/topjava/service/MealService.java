package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    @Autowired
    private final InMemoryMealRepository repository;

    public MealService(InMemoryMealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId) {
        meal.setUserId(userId);
        return repository.save(meal, userId);
    }

    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(Integer userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, Integer userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<Meal> getByDateFilter(Integer userId, LocalDate startDate, LocalDate endDate) {
        return repository.getByDateFilter(userId, startDate, endDate);
    }

    public List<Meal> getByTimeFilter(Integer userId, LocalTime startTime, LocalTime endTime) {
        return repository.getByTimeFilter(userId, startTime, endTime);
    }
}