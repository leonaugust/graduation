package ru.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.graduation.model.Vote;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.repository.UserRepository;
import ru.graduation.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(VoteService.class);

    public static final LocalTime VOTING_CLOSED = LocalTime.of(11, 0);

    public VoteService(VoteRepository voteRepository,
                       RestaurantRepository restaurantRepository,
                       UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote save(int userId, int restaurantId) {
        logger.info("save vote, userId {}, restaurantId {}", userId, restaurantId);
        Vote existing = findByUserId(userId);
        if (existing != null) {
            existing.setRestaurant(restaurantRepository.getOne(restaurantId));
            return voteRepository.save(existing);
        }

        Vote vote = new Vote();
        vote.setDate(LocalDate.now(getClock()));
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    public void delete(int id) {
        logger.info("delete vote {}", id);
        boolean found = voteRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Vote get(int id) {
        logger.info("get vote {}", id);
        Vote vote = voteRepository.findById(id).orElseThrow(null);
        return checkNotFoundWithId(vote, id);
    }

    public Vote findByUserId(int userId) {
        logger.info("find vote, userId {}", userId);
        return voteRepository.findByUserId(userId).orElse(null);
    }

    public List<Vote> getAll(int restaurantId) {
        logger.info("getAll votes, restaurantId {}", restaurantId);
        return voteRepository.getAll(restaurantId);
    }

    public List<Vote> findAllByDate(int restaurantId, LocalDate date) {
        logger.info("findAll votes by date, restaurantId {}, date {}", restaurantId, date);
        return voteRepository.findAllByDate(restaurantId, date);
    }
}
