package ru.graduation;

import ru.graduation.model.User;
import ru.graduation.to.UserTo;

import static ru.graduation.util.UserUtil.asTo;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), true, true,
                true, true, user.getRoles());
        this.userTo = asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
