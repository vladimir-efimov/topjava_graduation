package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.UserRepository;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.validation.exception.RepositoryOperationException;

@Service
public class VoteService extends AbstractBaseEntityService<Vote> {

    private final static LocalTime DEFAULT_END_VOTE_TIME = LocalTime.of(11, 0);
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final LocalTime endVotingTime;
    private final ClockHolder clockHolder;

    @Autowired
    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository,
                       ClockHolder clockHolder) {
        this(repository, restaurantRepository, userRepository, clockHolder, DEFAULT_END_VOTE_TIME);
    }

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository,
                       ClockHolder clockHolder, LocalTime endVotingTime) {
        super(repository);
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.clockHolder = clockHolder;
        this.endVotingTime = endVotingTime;
    }

    public Vote get(int id, int userId) {
        Vote vote = repository.get(id);
        if (!vote.getUser().getId().equals(userId)) {
            throw new SecurityException("User with id=" + userId + ":attempt to access not owned vote");
        }
        return vote;
    }

    @Override
    public Vote create(Vote vote) {
        Vote modifiedVote = new Vote(vote.getId(), getCurrentDateTime().toLocalDate(), vote.getUser(), vote.getRestaurant());
        return super.create(modifiedVote);
    }

    @Override
    public void update(Vote vote) {
        Vote modifiedVote = new Vote(vote.getId(), getCurrentDateTime().toLocalDate(), vote.getUser(), vote.getRestaurant());
        super.update(modifiedVote);
    }

    public List<Vote> findByUser(int userId) {
        return repository.findByUser(userId);
    }

    public List<Vote> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public Optional<Vote> findByUserAndDate(int userId, LocalDate date) {
        return repository.findByUserAndDate(userId, date);
    }

    public void delete(int id, int userId) {
        Vote entity = get(id, userId);
        validateOperation(entity, CrudOperation.DELETE);
        if (!repository.delete(id)) {
            throw new RepositoryOperationException("Can't delete " + entity.getClass().getSimpleName() + " with id " + id);
        }
    }

    public LocalTime getEndVotingTime() {
        return endVotingTime;
    }

    public void vote(int userId, int restaurantId) {
        Optional<Vote> result = findByUserAndDate(userId, LocalDate.now());
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with id = " + restaurantId + " is not found");
        }
        if (result.isPresent()) {
            Vote vote = result.get();
            vote.setRestaurant(restaurant);
            update(vote);
        } else {
            create(new Vote(userRepository.get(userId), restaurant));
        }
    }

    public void revoke(int userId) {
        Optional<Vote> result = findByUserAndDate(userId, LocalDate.now());
        if (result.isPresent()) {
            delete(result.get().getId(), userId);
        } else {
            throw new NotFoundException("No votes found to revoke");
        }
    }

    @Override
    protected void validateOperation(Vote vote, CrudOperation operation) {
        super.validateOperation(vote, operation);

        if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
            get(vote.getId(), vote.getUser().getId()); // assert vote is own
        }
        if (operation == CrudOperation.UPDATE && !getCurrentDateTime().toLocalTime().isBefore(endVotingTime)) {
            throw new IllegalOperationException("Updating vote is not allowed after " + endVotingTime);
        }
        if (beforeToday(vote.getDate())) {
            throw new IllegalOperationException("Can't operate with date in the past");
        }
    }


    private LocalDateTime getCurrentDateTime() {
            return LocalDateTime.now(clockHolder.getClock());
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDateTime().toLocalDate());
    }
}
