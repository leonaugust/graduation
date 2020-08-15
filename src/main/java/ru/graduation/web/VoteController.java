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
import ru.graduation.model.Vote;
import ru.graduation.repository.vote.VoteRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteRepository repository;

    private final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @GetMapping()
    public List<Vote> getAll(@RequestParam("restaurantId") int restaurantId) {
        logger.info("getAll votes");
        return repository.getAll(restaurantId);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        logger.info("get vote {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestParam("userId") int userId,
                                                   @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                                   @RequestParam("restaurantId") int restaurantId) {
        logger.info("create vote, userId: {}, dateTime: {}, restaurantId: {}", userId, dateTime, restaurantId);

        Vote created = repository.save(new Vote(dateTime),
                userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        logger.info("delete vote {}", id);
        repository.delete(id);
//        checkNotFoundWithId(repository.delete(id), id);
    }
}