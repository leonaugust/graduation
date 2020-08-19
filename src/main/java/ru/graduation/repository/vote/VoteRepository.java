package ru.graduation.repository.vote;

import org.springframework.stereotype.Repository;
import ru.graduation.model.Vote;
import ru.graduation.repository.restaurant.CrudRestaurantRepository;
import ru.graduation.repository.user.CrudUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.graduation.config.AppClock.getClock;
import static ru.graduation.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class VoteRepository {
    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public static final LocalTime VOTING_CLOSED = LocalTime.of(23, 0);

    public VoteRepository(CrudVoteRepository crudVoteRepository,
                          CrudRestaurantRepository crudRestaurantRepository,
                          CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    public Vote save(int userId, int restaurantId) {
        Vote existing = findByUserId(userId);
        if (existing != null) {
            existing.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
            return crudVoteRepository.save(existing);
        }

        Vote vote = new Vote();
        vote.setDate(LocalDate.now(getClock()));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    public void delete(int id) {
        boolean found = crudVoteRepository.delete(id) != 0;
        checkNotFoundWithId(found, id);
    }

    public Vote get(int id) {
        Vote vote = crudVoteRepository.findById(id).orElse(null);
        return checkNotFoundWithId(vote, id);
    }

    public Vote findByUserId(int userId) {
        return crudVoteRepository.findByUserId(userId).orElse(null);
    }

    public List<Vote> getAll(int restaurantId) {
        return crudVoteRepository.getAll(restaurantId);
    }

    public List<Vote> findAllByDate(int restaurantId, LocalDate date) {
        return crudVoteRepository.findAllByDate(restaurantId, date);
    }
}
