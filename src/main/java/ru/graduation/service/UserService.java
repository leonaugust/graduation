package ru.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.User;
import ru.graduation.repository.UserRepository;
import ru.graduation.to.UserTo;
import ru.graduation.util.UserUtil;
import ru.graduation.util.exception.NotFoundException;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        logger.info("create user {}", user.getId());
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public User create(UserTo userTo) {
        Assert.notNull(userTo, "user must not be null");
        return create(UserUtil.createNewFromTo(userTo));
    }

    public void update(User user) {
        logger.info("update user {}", user.getId());
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.id());
    }

    @Transactional
    public void update(UserTo userTo) {
        logger.info("update user");
        User user = get(userTo.id());
        UserUtil.updateFromTo(user, userTo);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.id());
    }

    public void delete(int id) {
        logger.info("delete user {}", id);
        boolean found = userRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public User get(int id) {
        logger.info("get user {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + id));
        return checkNotFoundWithId(user, id);
    }

    public List<User> getAll() {
        logger.info("getAll users");
        return userRepository.findAll(SORT_NAME);
    }

    public AuthorizedUser loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("load user by user name {}", login);
        User user = userRepository.getByLogin(login.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return new AuthorizedUser(user);
    }
}