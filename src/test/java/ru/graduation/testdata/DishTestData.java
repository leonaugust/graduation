package ru.graduation.testdata;

import ru.graduation.model.Dish;

import java.time.Month;

import static java.time.LocalDate.of;
import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {

    public static final int DISH1_ID = START_SEQ + 8;

    public static Dish getNew() {
        return new Dish(null, "Created dish", 1L, of(2020, Month.AUGUST, 14));
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Updated dish", 999L, of(2020, Month.AUGUST, 14));
    }
}