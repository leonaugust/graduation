package ru.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.graduation.model.Dish;
import ru.graduation.repository.DishRepository;
import ru.graduation.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    private final Logger logger = LoggerFactory.getLogger(DishService.class);

    public DishService(DishRepository crudRepository,
                       RestaurantRepository restaurantRepository) {
        this.dishRepository = crudRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish create(Dish dish, int restaurantId) {
        logger.info("create dish {}", dish.getId());
        Assert.notNull(dish, "dish must not be null");
        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now(getClock()));
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public void update(Dish dish, int restaurantId) {
        logger.info("update dish {}, restaurantId {}", dish.getId(), restaurantId);
        Assert.notNull(dish, "dish must not be null");
        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now(getClock()));
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(dishRepository.save(dish), dish.id());
    }

    public void delete(int id) {
        logger.info("delete dish {}", id);
        boolean found = dishRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Dish get(int id) {
        logger.info("get dish {}", id);
        Dish dish = dishRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(dish, id);
    }

    public List<Dish> getAll(int restaurantId) {
        logger.info("getAll dishes");
        return dishRepository.getAll(restaurantId);
    }

    public List<Dish> findAllByDate(int restaurantId, LocalDate date) {
        logger.info("findAll dishes by date");
        return dishRepository.findAllByDate(restaurantId, date);
    }
}
