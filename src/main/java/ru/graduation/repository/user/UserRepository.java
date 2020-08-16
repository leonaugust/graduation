package ru.graduation.repository.user;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.User;

import java.util.List;

@Repository("userRepository")
public class UserRepository implements UserDetailsService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudUserRepository crudRepository;

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User save(User user) {
        return crudRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
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