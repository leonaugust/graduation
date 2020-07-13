package ru.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import ru.graduation.model.User;
import ru.graduation.repository.UserRepository;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }
}
