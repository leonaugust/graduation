package ru.graduation.testdata;

import ru.graduation.model.Meal;

import java.time.Month;

import static java.time.LocalDate.of;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL1_ID = START_SEQ + 8;

    public static Meal getNew() {
        return new Meal(null, "Created meal", 1L, of(2020, Month.AUGUST, 14));
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Updated meal", 999L, of(2020, Month.AUGUST, 14));
    }
}