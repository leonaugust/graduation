package ru.graduation.repository.vote;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.graduation.model.Vote;
import ru.graduation.repository.restaurant.CrudRestaurantRepository;
import ru.graduation.repository.user.CrudUserRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
class AppClock {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

@Repository
public class VoteRepository {
    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;
    private final Clock clock;

    public VoteRepository(CrudVoteRepository crudVoteRepository,
                          CrudRestaurantRepository crudRestaurantRepository,
                          CrudUserRepository crudUserRepository, Clock clock) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
        this.clock = clock;
    }

    public Vote save(int restaurantId, int userId) {
        Vote v = new Vote();
        v.setDateTime(LocalDateTime.now(clock));
        v.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        v.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(v);
    }

    public boolean delete(int id) {
        return crudVoteRepository.delete(id) != 0;
    }

    public Vote get(int id) {
        return crudVoteRepository.findById(id).orElse(null);
    }

    public List<Vote> getAll(int restaurantId) {
        return crudVoteRepository.getAll(restaurantId);
    }
}
