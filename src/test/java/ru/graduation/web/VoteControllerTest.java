package ru.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.config.AppClock;
import ru.graduation.model.Vote;
import ru.graduation.service.VoteService;

import java.time.Clock;
import java.time.LocalDateTime;

import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.TestUtil.readFromJson;
import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.testdata.RestaurantTestData.*;
import static ru.graduation.testdata.UserTestData.*;
import static ru.graduation.testdata.VoteTestData.*;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Autowired
    private AppClock clock;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNotAllowed() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void createWithLocation() throws Exception {
        useFixedClockAt(ALLOWED_VOTING_TIME);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateWhenClosed() throws Exception {
        useFixedClockAt(TIME_AFTER_VOTING);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isLocked());
    }

    @Test
    void createWhenClosed() throws Exception {
        useFixedClockAt(TIME_AFTER_VOTING);

        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(TED))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isCreated());
    }

    @Test
    void createChangedOpinion() throws Exception {
        useFixedClockAt(ALLOWED_VOTING_TIME);
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isCreated());
        Vote created = readFromJson(action, Vote.class);

        assertThat(service.get(created.getId()).getRestaurant().getName())
                .isEqualTo(GUSTEAUS.getName());

        action = perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(USER))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID)))
                .andExpect(status().isCreated());
        Vote updated = readFromJson(action, Vote.class);

        assertThat(service.get(created.getId()).getRestaurant().getName())
                .isEqualTo(PIZZA_PLANET.getName());
        assertThat(created.id()).isEqualTo(updated.id());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(GUSTEAUS_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byDate")
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID))
                .param("date", "2020-08-14"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private void useFixedClockAt(LocalDateTime dateTime) {
        clock.setClock(Clock.fixed(dateTime.atZone(systemDefault()).toInstant(), systemDefault()));
    }
}
