package ru.graduation.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.AuthorizedUser;
import ru.graduation.View;
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

    private final MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping()
    public List<Meal> getAll(@RequestParam("restaurantId") int restaurantId) {
        return mealRepository.getAll(restaurantId);
    }

    @GetMapping("/byDate")
    public List<Meal> findAllByDate(@RequestParam("restaurantId") int restaurantId,
                                    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return mealRepository.findAllByDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return mealRepository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@AuthenticationPrincipal AuthorizedUser authUser,
                                                   @Validated(View.Web.class) @RequestBody Meal meal,
                                                   @RequestParam("restaurantId") int restaurantId) {
        checkNew(meal);
        Meal created = mealRepository.create(meal, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authUser,
                       @Validated(View.Web.class) @RequestBody Meal meal, @PathVariable int id,
                       @RequestParam("restaurantId") int restaurantId) {
        assureIdConsistent(meal, id);
        mealRepository.update(meal, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser,
                       @PathVariable int id) {
        mealRepository.delete(id);
    }
}
