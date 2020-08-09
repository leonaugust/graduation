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
import ru.graduation.model.Restaurant;
import ru.graduation.repository.restaurant.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.graduation.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantRepository repository;

    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    @GetMapping
    public List<Restaurant> getAll() {
        logger.info("getAll restaurants");
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        logger.info("get restaurant {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant r) {
        logger.info("create restaurant {}", r);
        checkNew(r);
        Assert.notNull(r, "restaurant must not be null");
        Restaurant created = repository.save(r);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant r, @PathVariable int id) {
        logger.info("update restaurant {} id {}", r, id);
        assureIdConsistent(r, id);
        Assert.notNull(r, "restaurant must not be null");
        checkNotFoundWithId(repository.save(r), r.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete restaurant {}", id);
        repository.delete(id);
//        checkNotFoundWithId(repository.delete(id), id);
    }
}
