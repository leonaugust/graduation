package ru.graduation.repository.meal;

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

    public MealRepository(CrudMealRepository crudRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Meal create(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        if (meal.getDate() == null) {
            meal.setDate(LocalDate.now(getClock()));
        }
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudMealRepository.save(meal);
    }

    public void update(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        if (meal.getDate() == null) {
            meal.setDate(LocalDate.now(getClock()));
        }
        meal.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(crudMealRepository.save(meal), meal.id());
    }

    public void delete(int id) {
        boolean found = crudMealRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Meal get(int id) {
        Meal meal = crudMealRepository.findById(id).orElse(null);
        return checkNotFoundWithId(meal, id);
    }

    public List<Meal> getAll(int restaurantId) {
        return crudMealRepository.getAll(restaurantId);
    }

    public List<Meal> findAllByDate(int restaurantId, LocalDate date) {
        return crudMealRepository.findAllByDate(restaurantId, date);
    }
}
