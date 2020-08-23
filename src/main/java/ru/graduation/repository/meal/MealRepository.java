package ru.graduation.repository.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.graduation.model.Meal;
import ru.graduation.repository.restaurant.CrudRestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class MealRepository {
    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    private final Logger logger = LoggerFactory.getLogger(MealRepository.class);

    public MealRepository(CrudMealRepository crudRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Meal create(Meal meal, int restaurantId) {
        logger.info("create meal {}", meal.getId());
        Assert.notNull(meal, "meal must not be null");
        if (meal.getDate() == null) {
            meal.setDate(LocalDate.now(getClock()));
        }
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMealRepository.save(meal);
    }

    public void update(Meal meal, int restaurantId) {
        logger.info("update meal {}, restaurantId {}", meal.getId(), restaurantId);
        Assert.notNull(meal, "meal must not be null");
        if (meal.getDate() == null) {
            meal.setDate(LocalDate.now(getClock()));
        }
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(crudMealRepository.save(meal), meal.id());
    }

    public void delete(int id) {
        logger.info("delete meal {}", id);
        boolean found = crudMealRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Meal get(int id) {
        logger.info("get meal {}", id);
        Meal meal = crudMealRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(meal, id);
    }

    public List<Meal> getAll(int restaurantId) {
        logger.info("getAll meals");
        return crudMealRepository.getAll(restaurantId);
    }

    public List<Meal> findAllByDate(int restaurantId, LocalDate date) {
        logger.info("findAll votes by date");
        return crudMealRepository.findAllByDate(restaurantId, date);
    }
}
