package ru.graduation.repository.user;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.User;

import java.util.List;

import static ru.graduation.util.ValidationUtil.*;

@Repository("userRepository")
public class UserRepository implements UserDetailsService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudUserRepository crudRepository;

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User create(User user) {
        checkNew(user);
        Assert.notNull(user, "user must not be null");
        return crudRepository.save(user);
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(crudRepository.save(user), user.id());
    }

    public void delete(int id) {
        boolean found = crudRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public User get(int id) {
        User user = crudRepository.findById(id).orElse(null);
        return checkNotFoundWithId(user, id);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME);
    }

    public AuthorizedUser loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = crudRepository.getByName(name.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + name + " is not found");
        }
        return new AuthorizedUser(user);
    }
}