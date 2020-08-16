package ru.graduation;

import ru.graduation.model.Meal;

import java.util.List;

import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static TestMatcher<Meal> MEAL_MATCHER =
            TestMatcher.usingFieldsComparator(Meal.class, "restaurant");

    public static final int NOT_FOUND = 10;
    public static final int MEAL1_ID = START_SEQ + 8;
    public static final int MEAL2_ID = START_SEQ + 9;
    public static final int MEAL3_ID = START_SEQ + 10;
    public static final int MEAL4_ID = START_SEQ + 11;
    public static final int MEAL5_ID = START_SEQ + 12;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, "Ratatouille", 25);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, "Buffalo Wings", 30);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, "Charbroiled Oysters", 27);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, "Meringue Pie", 10);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, "Omelet", 9);

    public static final List<Meal> MEALS = List.of(MEAL2, MEAL3, MEAL4, MEAL5, MEAL1);

    public static Meal getNew() {
        return new Meal(null, "Created meal", 0.01);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Updated meal", 9.99);
    }
}