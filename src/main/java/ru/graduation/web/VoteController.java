package ru.graduation.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.graduation.AuthorizedUser;
import ru.graduation.model.Vote;
import ru.graduation.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.graduation.config.AppClock.now;
import static ru.graduation.service.VoteService.VOTING_CLOSED;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String REST_URL = "/rest/votes";

    private final VoteService service;

    public VoteController(VoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vote> getAll(@RequestParam("restaurantId") int restaurantId) {
        return service.getAll(restaurantId);
    }

    @GetMapping("/byDate")
    public List<Vote> findAllByDate(@RequestParam("restaurantId") int restaurantId,
                                    @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.findAllByDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthorizedUser authUser,
                                                   @RequestParam("restaurantId") int restaurantId) {
        if (now().isAfter(VOTING_CLOSED)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Voting closed");
        }

        Vote created = service.save(authUser.getId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void delete(@PathVariable int id) {
        service.delete(id);
    }
}
