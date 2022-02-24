package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int NOT_FOUND = 100;

    public static final Meal meal1 = new Meal(100003,LocalDateTime.of(2021, Month.DECEMBER, 20, 20, 20, 22), "Обед", 600);
    public static final Meal meal2 = new Meal(100004,LocalDateTime.of(2021, Month.DECEMBER, 20, 20, 45, 22), "Завтрак", 680);
    public static final Meal meal3 = new Meal(100005,LocalDateTime.of(2021, Month.DECEMBER, 20, 20, 13, 22), "Ужин", 640);
    public static final Meal meal4 = new Meal(100006,LocalDateTime.of(2021, Month.DECEMBER, 22, 20, 20, 22), "Обед", 600);
    public static final Meal meal5 = new Meal(100007,LocalDateTime.of(2021, Month.DECEMBER, 21, 20, 45, 22), "Завтрак", 680);
    public static final Meal meal6 = new Meal(100008,LocalDateTime.of(2021, Month.DECEMBER, 23, 20, 13, 22), "Ужин", 640);
    public static final Meal meal7 = new Meal(100009,LocalDateTime.of(2021, Month.DECEMBER, 21, 20, 20, 22), "Обед", 600);
    public static final Meal meal8 = new Meal(100010,LocalDateTime.of(2021, Month.DECEMBER, 23, 20, 45, 22), "Завтрак", 680);
    public static final Meal meal9 = new Meal(100011,LocalDateTime.of(2021, Month.DECEMBER, 26, 20, 13, 22), "Ужин", 640);

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2018, Month.DECEMBER, 20, 20, 20, 22), "Обед", 600);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = meal2;
        updated.setDateTime(LocalDateTime.of(2021, Month.DECEMBER, 20, 20, 20, 22));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatchForMeal(Meal actual, Meal expected) {
        System.out.println(actual);
        System.out.println(expected);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatchForMeal(Iterable<Meal> actual, Meal... expected) {
        assertMatchForMeal(actual, Arrays.asList(expected));
    }

    public static void assertMatchForMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }


}
