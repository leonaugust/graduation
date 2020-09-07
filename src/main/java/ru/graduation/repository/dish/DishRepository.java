package ru.graduation.repository.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.graduation.model.Dish;
import ru.graduation.repository.restaurant.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DishRepository {
    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    private final Logger logger = LoggerFactory.getLogger(DishRepository.class);

    public DishRepository(CrudDishRepository crudRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Dish create(Dish dish, int restaurantId) {
        logger.info("create dish {}", dish.getId());
        Assert.notNull(dish, "dish must not be null");
        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now(getClock()));
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    public void update(Dish dish, int restaurantId) {
        logger.info("update dish {}, restaurantId {}", dish.getId(), restaurantId);
        Assert.notNull(dish, "dish must not be null");
        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now(getClock()));
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(crudDishRepository.save(dish), dish.id());
    }

    public void delete(int id) {
        logger.info("delete dish {}", id);
        boolean found = crudDishRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Dish get(int id) {
        logger.info("get dish {}", id);
        Dish dish = crudDishRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(dish, id);
    }

    public List<Dish> getAll(int restaurantId) {
        logger.info("getAll dishes");
        return crudDishRepository.getAll(restaurantId);
    }

    public List<Dish> findAllByDate(int restaurantId, LocalDate date) {
        logger.info("findAll dishes by date");
        return crudDishRepository.findAllByDate(restaurantId, date);
    }
}
