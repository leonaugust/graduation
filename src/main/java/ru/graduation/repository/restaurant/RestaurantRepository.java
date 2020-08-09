package ru.graduation.repository.restaurant;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.graduation.model.Restaurant;

import java.util.List;

@Repository
public class RestaurantRepository {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Restaurant save(Restaurant r) {
        return crudRepository.save(r);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_NAME);
    }
}
