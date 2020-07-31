package ru.graduation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import ru.graduation.model.User;
import ru.graduation.repository.UserRepository;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class UserController {
    private final UserRepository repository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        logger.info("getAll users");
        return repository.getAll();
    }

    public User get(int id) {
        logger.info("get user {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User create(User user) {
        logger.info("create user {}", user);
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void update(User user, int id) {
        logger.info("update user {} id {}", user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), id);
    }

    public void delete(int id) {
        logger.info("delete user {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }
}
