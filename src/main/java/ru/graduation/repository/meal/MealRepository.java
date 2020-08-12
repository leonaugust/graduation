package ru.graduation.repository.meal;

import org.springframework.stereotype.Repository;
import ru.graduation.model.Meal;

import java.util.List;

@Repository
public class MealRepository {
    private final CrudMealRepository crudRepository;

    public MealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Meal save(Meal m) {
        return crudRepository.save(m);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Meal get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Meal> getAll(int restaurantId) {
        return crudRepository.getAll(restaurantId);
    }
}
