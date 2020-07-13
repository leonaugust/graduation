package ru.graduation.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.graduation.model.User;
import ru.graduation.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.graduation.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserControllerTest {
    @Autowired
    private UserController controller;

    @Test
    public void create() {
        User created = controller.create(getNew());
        int newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(controller.get(newId), newUser);
    }

    @Test
    public void update() {
        User updated = getUpdated();
        controller.update(updated, updated.id());
        USER_MATCHER.assertMatch(controller.get(USER_ID), getUpdated());
    }

    @Test
    public void get() {
        User user = controller.get(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        Assert.assertEquals(user, USER);
    }

    @Test
    public void getAll() {
        List<User> all = controller.getAll();
        USER_MATCHER.assertMatch(all, ADMIN, USER);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}
