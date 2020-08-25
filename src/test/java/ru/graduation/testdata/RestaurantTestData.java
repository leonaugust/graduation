package ru.graduation.testdata;

import ru.graduation.model.Restaurant;

import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final int GUSTEAUS_ID = START_SEQ + 5;
    public static final int PIZZA_PLANET_ID = START_SEQ + 6;
    public static final int KRUSTY_KRAB_ID = START_SEQ + 7;

    public static final Restaurant GUSTEAUS = new Restaurant(GUSTEAUS_ID, "Gusteau's");
    public static final Restaurant PIZZA_PLANET = new Restaurant(PIZZA_PLANET_ID, "Pizza Planet");

    public static Restaurant getNew() {
        return new Restaurant(null, "Created restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(KRUSTY_KRAB_ID, "Updated restaurant");
    }
}
