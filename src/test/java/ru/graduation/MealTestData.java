package ru.graduation;

import ru.graduation.model.Meal;

import java.util.List;

import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static TestMatcher<Meal> MEAL_MATCHER =
            TestMatcher.usingFieldsComparator(Meal.class, "restaurant");

    public static final int NOT_FOUND = 10;
    public static final int MEAL1_ID = START_SEQ + 5;
    public static final int MEAL2_ID = START_SEQ + 6;
    public static final int MEAL3_ID = START_SEQ + 7;
    public static final int MEAL4_ID = START_SEQ + 8;
    public static final int MEAL5_ID = START_SEQ + 9;
    public static final int MEAL6_ID = START_SEQ + 10;
    public static final int MEAL7_ID = START_SEQ + 11;
    public static final int MEAL8_ID = START_SEQ + 12;
    public static final int MEAL9_ID = START_SEQ + 13;
    public static final int MEAL10_ID = START_SEQ + 14;
    public static final int MEAL11_ID = START_SEQ + 15;
    public static final int MEAL12_ID = START_SEQ + 16;
    public static final int MEAL13_ID = START_SEQ + 17;
    public static final int MEAL14_ID = START_SEQ + 18;
    public static final int MEAL15_ID = START_SEQ + 19;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, "Ratatouille", 25);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, "Buffalo Wings", 30);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, "Charbroiled Oysters", 27);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, "Meringue Pie", 10);
    public static final Meal MEAL5 = new Meal(MEAL5_ID, "Omelet", 9);

    public static final Meal MEAL6 = new Meal(MEAL6_ID, "Pepperoni Pizza", 10.99);
    public static final Meal MEAL7 = new Meal(MEAL7_ID, "New York-Style Pizza", 9.54);
    public static final Meal MEAL8 = new Meal(MEAL8_ID, "Greek-Style Pizza", 8);
    public static final Meal MEAL9 = new Meal(MEAL9_ID, "Sicilian Pizza", 12);
    public static final Meal MEAL10 = new Meal(MEAL10_ID, "Tomato Pie", 9);

    public static final Meal MEAL11 = new Meal(MEAL11_ID, "Cheeseburger", 6.55);
    public static final Meal MEAL12 = new Meal(MEAL12_ID, "The Original Burger", 5);
    public static final Meal MEAL13 = new Meal(MEAL13_ID, "Luger Burger", 7.29);
    public static final Meal MEAL14 = new Meal(MEAL14_ID, "Bacon Cheeseburger", 7);
    public static final Meal MEAL15 = new Meal(MEAL15_ID, "Bob's Burger Barn", 6.23);


    public static final List<Meal> MEALS = List.of(MEAL14, MEAL15, MEAL2, MEAL3, MEAL11, MEAL8, MEAL13,
            MEAL4, MEAL7, MEAL5, MEAL6, MEAL1, MEAL9, MEAL12, MEAL10);

    public static Meal getNew() {
        return new Meal(null, "Created meal", 0.01);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Updated meal", 9.99);
    }
}