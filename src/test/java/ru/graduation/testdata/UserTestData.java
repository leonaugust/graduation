package ru.graduation.testdata;

import ru.graduation.model.Role;
import ru.graduation.model.User;

import java.util.Collections;

public class UserTestData {
    public static final int USER_ID = User.START_SEQ;
    public static final int ADMIN_ID = User.START_SEQ + 1;
    public static final int BARNEY_ID = User.START_SEQ + 2;

    public static final User USER = new User(USER_ID, "user", "user", "password", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin", "admin", "password", Role.ADMIN);
    public static final User BARNEY = new User(BARNEY_ID, "barney", "stinson", "password", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new", "password", Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User();
        updated.setName("UpdatedName");
        updated.setLogin("UpdatedLogin");
        updated.setPassword("UpdatedPassword");
        return updated;
    }
}
