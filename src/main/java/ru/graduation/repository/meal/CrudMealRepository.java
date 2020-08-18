package ru.graduation.repository.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId ORDER BY m.name ASC")
    List<Meal> getAll(@Param("restaurantId") int restaurantId);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restaurantId AND m.date=:date ORDER BY m.name ASC")
    List<Meal> findAllByDate(@Param("restaurantId") int restaurantId,
                             @Param("date") LocalDate date);
}
