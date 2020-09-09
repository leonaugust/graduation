package ru.graduation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.model.Restaurant;
import ru.graduation.model.Vote;
import ru.graduation.repository.RestaurantRepository;
import ru.graduation.repository.UserRepository;
import ru.graduation.repository.VoteRepository;
import ru.graduation.util.exception.NotFoundException;
import ru.graduation.util.exception.VotingClosedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.config.AppClock.now;
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

    @Transactional
    public Vote save(int userId, int restaurantId) {
        logger.info("save vote, userId {}, restaurantId {}", userId, restaurantId);
        LocalDate today = LocalDate.now(getClock());
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        Vote vote = findByUserIdAndDate(userId, today);

        if (now().isAfter(VOTING_CLOSED) && !vote.isNew()) {
            throw new VotingClosedException("Voting closed");
        }

        if (vote.isNew()) {
            vote = new Vote(userRepository.getOne(userId), today, restaurant);
        } else {
            vote.setRestaurant(restaurant);
        }
        return voteRepository.save(vote);
    }

    public void delete(int id) {
        logger.info("delete vote {}", id);
        boolean found = voteRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Vote get(int id) {
        logger.info("get vote {}", id);
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vote not found with id = " + id));
        return checkNotFoundWithId(vote, id);
    }

    public Vote findByUserIdAndDate(int userId, LocalDate date) {
        logger.info("find vote, userId {}, date {}", userId, date);
        Vote vote = voteRepository.findByUserIdAndDate(userId, date);
        if (vote == null) {
            return new Vote();
        }
        return vote;
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
