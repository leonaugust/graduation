package ru.graduation.repository.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.user.CrudUserRepository;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class RestaurantRepository {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    private final Logger logger = LoggerFactory.getLogger(RestaurantRepository.class);

    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository,
                                CrudUserRepository crudUserRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    public Restaurant create(Restaurant r, int userId) {
        logger.info("create restaurant {}", r.getId());
        Assert.notNull(r, "restaurant must not be null");
        r.setUser(crudUserRepository.getOne(userId));
        return crudRestaurantRepository.save(r);
    }

    public void update(Restaurant r, int userId) {
        logger.info("update restaurant {}", r.getId());
        Assert.notNull(r, "restaurant must not be null");
        r.setUser(crudUserRepository.getOne(userId));
        checkNotFoundWithId(crudRestaurantRepository.save(r), r.id());
    }

    public void delete(int id) {
        logger.info("delete restaurant {}", id);
        boolean found = crudRestaurantRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Restaurant get(int id) {
        logger.info("get restaurant {}", id);
        Restaurant restaurant = crudRestaurantRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(restaurant, id);
    }

    public List<Restaurant> getAll() {
        logger.info("getAll restaurants");
        return crudRestaurantRepository.findAll(SORT_NAME);
    }
}
