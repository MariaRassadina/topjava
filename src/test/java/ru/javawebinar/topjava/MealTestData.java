package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL1_ID_USER = START_SEQ + 3;
    public static final int MEAL2_ID_USER = START_SEQ + 4;
    public static final int MEAL3_ID_USER = START_SEQ + 5;
    public static final int MEAL4_ID_USER = START_SEQ + 6;
    public static final int MEAL5_ID_ADMIN = START_SEQ + 7;
    public static final int MEAL6_ID_ADMIN = START_SEQ + 8;
    public static final int MEAL7_ID_ADMIN = START_SEQ + 9;
    public static final int NOT_FOUND = 10;

    public static final Meal mealUser1 = new Meal(MEAL1_ID_USER, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 8, 0, 0)), "завтрак1 User", 600);
    public static final Meal mealUser2 = new Meal(MEAL2_ID_USER, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 13, 0, 0)), "обед1 User", 800);
    public static final Meal mealUser3 = new Meal(MEAL3_ID_USER, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 18, 0, 0)), "ужин1 User", 500);
    public static final Meal mealUser4 = new Meal(MEAL4_ID_USER, (LocalDateTime.of(2023, Month.FEBRUARY, 18, 8, 0, 0)), "завтрак2 User", 800);
    public static final Meal mealAdmin5 = new Meal(MEAL5_ID_ADMIN, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 8, 0, 0)), "завтрак Admin", 600);
    public static final Meal mealAdmin6 = new Meal(MEAL6_ID_ADMIN, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 13, 30, 0)), "обед Admin", 800);
    public static final Meal mealAdmin7 = new Meal(MEAL7_ID_ADMIN, (LocalDateTime.of(2023, Month.FEBRUARY, 19, 19, 0, 0)), "ужин Admin", 400);

    public static final List<Meal> meals = Arrays.asList(mealUser3, mealUser2, mealUser1, mealUser4);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "New", 880);
    }

    public static Meal getUpdate() {
        Meal updated = new Meal();
        updated.setId(MEAL1_ID_USER);
        updated.setDateTime(LocalDateTime.of(2023, Month.JANUARY, 1, 1, 1, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static Meal getCreate() {
        return new Meal(null, LocalDateTime.of(2023, Month.JANUARY, 1, 1, 1, 0), "newDescription", 660);
    }

    public static void assertMatchMeal(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Meal... expected) {
        assertMatchMeal(actual, Arrays.asList(expected));
    }

    public static void assertMatchMeal(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
