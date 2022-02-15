package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (!meal.getUserId().equals(userId)) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId)) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId)) {
            return null;
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> getByDateFilter(Integer userId, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return getAll(userId);
        }
        if (startDate == null) {
            return getAll(userId).stream()
                    .filter(meal -> meal.getDateTime().toLocalDate().compareTo(endDate) <= 0)
                    .collect(Collectors.toList());
        }
        if (endDate == null) {
            return getAll(userId).stream()
                    .filter(meal -> meal.getDateTime().toLocalDate().compareTo(startDate) >= 0)
                    .collect(Collectors.toList());
        }
        else return getAll(userId).stream()
                .filter(meal -> meal.getDateTime().toLocalDate().compareTo(startDate) >= 0)
                .filter(meal -> meal.getDateTime().toLocalDate().compareTo(endDate) <= 0)
                .collect(Collectors.toList());
    }

    public List<Meal> getByTimeFilter(Integer userId, LocalTime startTime, LocalTime endTime) {
        if (startTime == null && endTime == null) {
            return getAll(userId);
        }
        if (startTime == null) {
            return getAll(userId).stream()
                    .filter(meal -> meal.getDateTime().toLocalTime().compareTo(endTime) <= 0)
                    .collect(Collectors.toList());
        }
        if (endTime == null) {
            return getAll(userId).stream()
                    .filter(meal -> meal.getDateTime().toLocalTime().compareTo(startTime) >= 0)
                    .collect(Collectors.toList());
        }
        else return getAll(userId).stream()
                .filter(meal -> meal.getDateTime().toLocalTime().compareTo(startTime) >= 0)
                .filter(meal -> meal.getDateTime().toLocalTime().compareTo(endTime) <= 0)
                .collect(Collectors.toList());
    }
}

