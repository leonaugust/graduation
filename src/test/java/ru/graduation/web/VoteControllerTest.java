package ru.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.UserTestData;
import ru.graduation.model.Vote;
import ru.graduation.repository.vote.VoteRepository;
import ru.graduation.util.exception.NotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.RestaurantTestData.*;
import static ru.graduation.TestUtil.readFromJson;
import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.UserTestData.ADMIN;
import static ru.graduation.UserTestData.USER;
import static ru.graduation.VoteTestData.*;

public class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteController controller;

    @Autowired
    private VoteRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE1));
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
        assertThrows(NotFoundException.class, () -> controller.get(VOTE1_ID));
    }

    //    https://stackoverflow.com/questions/24491260/mocking-time-in-java-8s-java-time-api
    @Test
    void createWithLocation() throws Exception {
        useFixedClockAt(ALLOWED_VOTING_TIME);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .param("userId", String.valueOf(UserTestData.USER_ID))
                .param("restaurantId", String.valueOf(RATATOUILLE_ID)))
                .andExpect(status().isCreated());
    }

    @Test
    void createWhenClosed() throws Exception {
        useFixedClockAt(TIME_AFTER_VOTING);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .param("userId", String.valueOf(UserTestData.USER_ID))
                .param("restaurantId", String.valueOf(RATATOUILLE_ID)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createChangedOpinion() throws Exception {
        useFixedClockAt(SIX_HOURS);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .param("userId", String.valueOf(UserTestData.USER_ID))
                .param("restaurantId", String.valueOf(RATATOUILLE_ID)))
                .andExpect(status().isCreated());
        Vote created = readFromJson(action, Vote.class);

        assertThat(controller.get(created.getId()).getRestaurant().getName())
                .isEqualTo(RATATOUILLE.getName());

        useFixedClockAt(SEVEN_HOURS);
        action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .param("userId", String.valueOf(UserTestData.USER_ID))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID)))
                .andExpect(status().isCreated());
        Vote updated = readFromJson(action, Vote.class);

        assertThat(controller.get(created.getId()).getRestaurant().getName())
                .isEqualTo(PIZZA_PLANET.getName());
        assertThat(created.id()).isEqualTo(updated.id());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(RATATOUILLE_ID)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE1, VOTE2, VOTE4)));
    }

    @Test
    void findAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byDate")
                .with(userHttpBasic(ADMIN))
                .param("restaurantId", String.valueOf(PIZZA_PLANET_ID))
                .param("date", "2020-08-14"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(VOTE3)));
    }

    void useFixedClockAt(LocalDateTime dateTime) {
        repository.setClock(Clock.fixed(dateTime.atZone(systemDefault()).toInstant(), systemDefault()));
    }
}
