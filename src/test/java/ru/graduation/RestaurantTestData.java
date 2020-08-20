package ru.graduation;

import ru.graduation.model.Restaurant;

import java.util.List;

import static ru.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER =
            TestMatcher.usingFieldsWithIgnoringAssertions(Restaurant.class, "menu", "user");

    public static final int NOT_FOUND = 10;
    public static final int RATATOUILLE_ID = START_SEQ + 5;
    public static final int PIZZA_PLANET_ID = START_SEQ + 6;
    public static final int KRUSTY_KRAB_ID = START_SEQ + 7;

    public static final Restaurant RATATOUILLE = new Restaurant(RATATOUILLE_ID, "La Ratatouille", "Disney universe", "www.la-ratatouille.com");
    public static final Restaurant PIZZA_PLANET = new Restaurant(PIZZA_PLANET_ID, "Pizza Planet", "Disney universe", "www.pizza-planet.com");
    public static final Restaurant KRUSTY_KRAB = new Restaurant(KRUSTY_KRAB_ID, "The Krusty Krab ", "831 Bottom Feeder Lane", "www.krusty-krab.com");


    public static final List<Restaurant> RESTAURANTS = List.of(RATATOUILLE, PIZZA_PLANET, KRUSTY_KRAB);

    public static Restaurant getNew() {
        return new Restaurant(null, "Created restaurant", "Created address", "Created website");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(KRUSTY_KRAB_ID, "Updated restaurant", "Updated address", "Updated website");
    }
}
