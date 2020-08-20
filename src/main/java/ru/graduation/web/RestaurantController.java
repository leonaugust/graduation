package ru.graduation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.Restaurant;
import ru.graduation.repository.restaurant.RestaurantRepository;

import java.net.URI;
import java.util.List;

import static ru.graduation.util.ValidationUtil.assureIdConsistent;
import static ru.graduation.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    static final String REST_URL = "/rest/restaurants";

    private final RestaurantRepository repository;

    private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

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
    public ResponseEntity<Restaurant> createWithLocation(@AuthenticationPrincipal AuthorizedUser authUser,
                                                         @RequestBody Restaurant r) {
        logger.info("create restaurant {}", r);
        checkNew(r);
        Restaurant created = repository.create(r, authUser.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authUser,
                       @RequestBody Restaurant r,
                       @PathVariable int id) {
        logger.info("update restaurant {} id {}", r, id);
        int userId = authUser.getId();
        checkOwner(id, userId);
        assureIdConsistent(r, id);
        repository.update(r, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser,
                       @PathVariable int id) {
        logger.info("delete restaurant {}", id);
        checkOwner(id, authUser.getId());
        repository.delete(id);
    }

    public void checkOwner(int restaurantId, int userId) {
        if (repository.get(restaurantId).getUser().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not allowed to do this");
        }
    }
}
