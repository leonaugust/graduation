package ru.graduation.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.model.Dish;
import ru.graduation.testdata.DishTestData;
import ru.graduation.web.json.JsonUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.TestUtil.readFromJson;
import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.testdata.DishTestData.DISH1_ID;
import static ru.graduation.testdata.RestaurantTestData.GUSTEAUS_ID;
import static ru.graduation.testdata.UserTestData.ADMIN;

public class DishControllerTest extends AbstractControllerTest {
    private static final String REST_URL = DishController.REST_URL + '/';

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isOk());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
    }

    @Test
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
    }

    @Test
    void createDateNotAssigned() throws Exception {
        Dish dish = new Dish(null, "test", 210L);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish)))
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        Assertions.assertThat(created.getDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byDate")
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID))
                .param("date", "2020-08-14"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}