package ru.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.name ASC")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date ORDER BY d.name ASC")
    List<Dish> findMenuByDate(@Param("restaurantId") int restaurantId,
                              @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.date=:date ORDER BY d.name ASC")
    List<Dish> findAllForDate(@Param("date") LocalDate date);
}
