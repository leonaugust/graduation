package ru.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.model.Restaurant;
import ru.graduation.util.exception.NotFoundException;
import ru.graduation.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.RestaurantTestData.*;
import static ru.graduation.TestUtil.readFromJson;
import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.UserTestData.ADMIN;
import static ru.graduation.UserTestData.ADMIN_ID;

public class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantController.REST_URL + '/';

    @Autowired
    private RestaurantController controller;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KRUSTY_KRAB_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(KRUSTY_KRAB));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + KRUSTY_KRAB_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + KRUSTY_KRAB_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> controller.get(KRUSTY_KRAB_ID));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + KRUSTY_KRAB_ID)
                .with(userHttpBasic(ADMIN))
                .param("userId", String.valueOf(ADMIN_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(controller.get(KRUSTY_KRAB_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("userId", String.valueOf(ADMIN_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(controller.get(newId), newRestaurant);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANTS));
    }
}
