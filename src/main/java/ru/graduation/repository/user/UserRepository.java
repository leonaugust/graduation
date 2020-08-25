package ru.graduation.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.User;
import ru.graduation.to.UserTo;
import ru.graduation.util.UserUtil;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Repository("userRepository")
public class UserRepository implements UserDetailsService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudUserRepository crudRepository;

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User create(User user) {
        logger.info("create user {}", user.getId());
        Assert.notNull(user, "user must not be null");
        return crudRepository.save(user);
    }

    public User create(UserTo userTo) {
        Assert.notNull(userTo, "user must not be null");
        return create(UserUtil.createNewFromTo(userTo));
    }

    public void update(User user) {
        logger.info("update user {}", user.getId());
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(crudRepository.save(user), user.id());
    }

    public void update(UserTo userTo) {
        logger.info("update user");
        User user = get(userTo.id());
        UserUtil.updateFromTo(user, userTo);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(crudRepository.save(user), user.id());
    }

    public void delete(int id) {
        logger.info("delete user {}", id);
        boolean found = crudRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public User get(int id) {
        logger.info("get user {}", id);
        User user = crudRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(user, id);
    }

    public List<User> getAll() {
        logger.info("getAll users");
        return crudRepository.findAll(SORT_NAME);
    }

    public AuthorizedUser loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.info("load user by user name {}", login);
        User user = crudRepository.getByLogin(login.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " is not found");
        }
        return new AuthorizedUser(user);
    }
}