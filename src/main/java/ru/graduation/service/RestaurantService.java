package ru.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.util.exception.NotFoundException;

import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository restaurantRepository;

    private final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant create(Restaurant r) {
        logger.info("create restaurant {}", r.getId());
        Assert.notNull(r, "restaurant must not be null");
        return restaurantRepository.save(r);
    }

    public void update(Restaurant r) {
        logger.info("update restaurant {}", r.getId());
        Assert.notNull(r, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(r), r.id());
    }

    public void delete(int id) {
        logger.info("delete restaurant {}", id);
        boolean found = restaurantRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Restaurant get(int id) {
        logger.info("get restaurant {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id = " + id));
        return checkNotFoundWithId(restaurant, id);
    }

    public List<Restaurant> getAll() {
        logger.info("getAll restaurants");
        return restaurantRepository.findAll(SORT_NAME);
    }
}
