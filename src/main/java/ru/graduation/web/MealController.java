package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.model.Meal;
import ru.graduation.repository.meal.MealRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MealController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealController {
    static final String REST_URL = "/rest/meals";

    @Autowired
    private MealRepository repository;

    private final Logger logger = LoggerFactory.getLogger(MealController.class);

    @GetMapping()
    public List<Meal> getAll(@RequestParam int id) {
        logger.info("getAll meals");
        return repository.getAll(id);
    }

    @GetMapping("/byDate")
    public List<Meal> findAllByDate(@RequestParam("restaurantId") int restaurantId,
                                    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        logger.info("findAll votes by date");
        return repository.findAllByDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        logger.info("get meal {}", id);
        return repository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal,
                                                   @RequestParam("restaurantId") int restaurantId) {
        logger.info("create meal {}", meal);
        checkNew(meal);
        Meal created = repository.create(meal, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id,
                       @RequestParam("restaurantId") int restaurantId) {
        logger.info("update meal {} id {}", meal, id);
        assureIdConsistent(meal, id);
        repository.update(meal, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete meal {}", id);
        repository.delete(id);
    }
}
