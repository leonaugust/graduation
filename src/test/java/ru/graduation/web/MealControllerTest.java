package ru.graduation.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.model.Meal;
import ru.graduation.repository.meal.MealRepository;
import ru.graduation.testdata.MealTestData;
import ru.graduation.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.TestUtil.readFromJson;
import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.testdata.MealTestData.*;
import static ru.graduation.testdata.RestaurantTestData.GUSTEAUS_ID;
import static ru.graduation.testdata.RestaurantTestData.PIZZA_PLANET_ID;
import static ru.graduation.testdata.UserTestData.ADMIN;
import static ru.graduation.testdata.UserTestData.BARNEY;

public class MealControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealController.REST_URL + '/';

    @Autowired
    private MealRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL1));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(repository.getAll(GUSTEAUS_ID), MEAL2, MEAL3, MEAL4, MEAL5);
    }

    @Test
    void deleteNotOwner() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID)
                .with(userHttpBasic(BARNEY)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(repository.get(MEAL1_ID), updated);
    }

    @Test
    void updateNotOwner() throws Exception {
        Meal updated = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal created = readFromJson(action, Meal.class);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(repository.get(newId), newMeal);
    }

    @Test
    void createDateNotAssigned() throws Exception {
        Meal meal = new Meal(null, "test", 210L);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());

        Meal created = readFromJson(action, Meal.class);
        Assertions.assertThat(created.getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void createNotOwner() throws Exception {
        Meal newMeal = MealTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEALS));
    }

    @Test
    void findAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byDate")
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .param("date", "2020-08-14"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(List.of(MEAL2, MEAL3, MEAL4, MEAL5, MEAL1)));
    }
}
