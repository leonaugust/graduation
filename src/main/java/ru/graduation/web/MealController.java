package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.model.Meal;
import ru.graduation.repository.meal.MealRepository;

import java.net.URI;
import java.util.List;

import static ru.graduation.util.ValidationUtil.*;

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

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        logger.info("get meal {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal m) {
        logger.info("create meal {}", m);
        checkNew(m);
        Assert.notNull(m, "meal must not be null");
        Meal created = repository.save(m);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal m, @PathVariable int id) {
        logger.info("update meal {} id {}", m, id);
        assureIdConsistent(m, id);
        Assert.notNull(m, "meal must not be null");
        checkNotFoundWithId(repository.save(m), m.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete meal {}", id);
        repository.delete(id);
//        checkNotFoundWithId(repository.delete(id), id);
    }
}
