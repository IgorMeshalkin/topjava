package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;
import java.util.Objects;


public class MealDao {

    public List<MealTo> getAll() {
        return MealsUtil.timelessMapper(MealsUtil.meals);
    }

    public Meal getById (Integer id) {
        return MealsUtil.meals.stream()
                .filter(m -> Objects.equals(m.getId(), id)).findFirst()
                .orElse(null);
    }

    public void add (Meal meal) {
        MealsUtil.meals.add(meal);
    }

    public void update (Meal meal) {
        MealsUtil.meals.stream()
                .filter(m -> Objects.equals(m.getId(), meal.getId())).findFirst()
                .ifPresent(updateMeal -> MealsUtil.meals.set(MealsUtil.meals.indexOf(updateMeal), meal));
    }

    public void delete(Integer id) {
        MealsUtil.meals.stream()
                .filter(m -> Objects.equals(m.getId(), id)).findFirst()
                .ifPresent(meal -> MealsUtil.meals.remove(meal));
    }
}
