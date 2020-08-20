package ru.graduation.util;

import ru.graduation.model.Role;
import ru.graduation.model.User;
import ru.graduation.to.UserTo;

public class UserUtil {

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getLogin().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLogin(), user.getPassword());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setLogin(userTo.getLogin().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }
}
