package ru.graduation.repository.meal;

import org.springframework.stereotype.Repository;
import ru.graduation.model.Meal;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Repository
public class MealRepository {
    private final CrudMealRepository crudRepository;
    private final Clock clock;

    public MealRepository(CrudMealRepository crudRepository, Clock clock) {
        this.crudRepository = crudRepository;
        this.clock = clock;
    }

    public Meal save(Meal m) {
        if (m.getDate() == null) {
            m.setDate(LocalDate.now(clock));
        }
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
