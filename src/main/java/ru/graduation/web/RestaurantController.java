package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.restaurant.RestaurantRepository;

import java.net.URI;
import java.util.List;

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
        return repository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant r,
                                                         @RequestParam("userId") int userId) {
        logger.info("create restaurant {}", r);
        Restaurant created = repository.create(r, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant r, @PathVariable int id,
                       @RequestParam("userId") int userId) {
        logger.info("update restaurant {} id {}", r, id);
        repository.update(id, r, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete restaurant {}", id);
        repository.delete(id);
    }
}
