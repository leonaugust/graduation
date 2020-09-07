package ru.graduation.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.View;
import ru.graduation.model.Dish;
import ru.graduation.repository.dish.DishRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    static final String REST_URL = "/rest/dishes";

    private final DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping()
    public List<Dish> getAll(@RequestParam("restaurantId") int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    @GetMapping("/byDate")
    public List<Dish> findAllByDate(@RequestParam("restaurantId") int restaurantId,
                                    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dishRepository.findAllByDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return dishRepository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated(View.Web.class) @RequestBody Dish dish,
                                                   @RequestParam("restaurantId") int restaurantId) {
        checkNew(dish);
        Dish created = dishRepository.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int id,
                       @RequestParam("restaurantId") int restaurantId) {
        assureIdConsistent(dish, id);
        dishRepository.update(dish, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        dishRepository.delete(id);
    }
}
