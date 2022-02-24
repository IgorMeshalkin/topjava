package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.NOT_FOUND;
import static ru.javawebinar.topjava.MealTestData.assertMatchForMeal;
import static ru.javawebinar.topjava.MealTestData.getNewMeal;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(),USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMatchForMeal(created, newMeal);
        assertMatchForMeal(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2021, Month.DECEMBER, 20, 20, 20, 22), "Перекус", 350),USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MealTestData.meal1.getId(),USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MealTestData.meal1.getId(),USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND,USER_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(MealTestData.meal1.getId(),USER_ID);
        assertMatchForMeal(meal, MealTestData.meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND,USER_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        service.update(updated, ADMIN_ID);
        assertMatchForMeal(service.get(updated.getId(), ADMIN_ID), getUpdatedMeal());
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(GUEST_ID);
        assertMatchForMeal(all, meal9, meal6, meal3);
    }

    @Test
    public void deleteMealBelongsToAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(meal1.getId(), GUEST_ID));
    }

    @Test
    public void getMealBelongsToAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), GUEST_ID));
    }

    @Test
    public void updateMealBelongsToAnotherUser() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, GUEST_ID));
    }

    @Test
    public void duplicateDateTime() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2021, Month.DECEMBER, 23, 20, 45, 22), "duplicateTest", 550), ADMIN_ID));
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2021, Month.DECEMBER, 21), LocalDate.of(2021, Month.DECEMBER, 23), ADMIN_ID);
        assertMatchForMeal(all, meal8, meal5);
    }
}