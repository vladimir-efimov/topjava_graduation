package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.JpaRestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.JpaUserRepository;
import ru.javawebinar.topjavagraduation.repository.JpaVoteRepository;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.exception.NotFoundException;


@Service
public class VoteService extends AbstractBaseEntityService<Vote> {

    private final static LocalTime DEFAULT_END_VOTE_TIME = LocalTime.of(11, 0);
    private final JpaVoteRepository repository;
    private final JpaRestaurantRepository restaurantRepository;
    private final JpaUserRepository userRepository;
    private final LocalTime endVotingTime;
    private final ClockHolder clockHolder;

    @Autowired
    public VoteService(JpaVoteRepository repository, JpaRestaurantRepository restaurantRepository, JpaUserRepository userRepository,
                       ClockHolder clockHolder) {
        this(repository, restaurantRepository, userRepository, clockHolder, DEFAULT_END_VOTE_TIME);
    }

    public VoteService(JpaVoteRepository repository, JpaRestaurantRepository restaurantRepository, JpaUserRepository userRepository,
                       ClockHolder clockHolder, LocalTime endVotingTime) {
        super(repository);
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.clockHolder = clockHolder;
        this.endVotingTime = endVotingTime;
    }

    public Vote get(int id, int userId) {
        Vote vote = super.get(id);
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

    public Vote create(int userId, int restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        return create(new Vote(getUser(userId), restaurant));
    }

    @Override
    public void update(Vote vote) {
        Vote modifiedVote = new Vote(vote.getId(), getCurrentDateTime().toLocalDate(), vote.getUser(), vote.getRestaurant());
        super.update(modifiedVote);
    }

    public void update(int userId, int restaurantId) {
        Vote vote = findByUserAndDate(userId, getCurrentDateTime().toLocalDate())
                .orElseThrow(() -> new NotFoundException("Today's vote for user is not found"));
        Restaurant restaurant = getRestaurant(restaurantId);
        vote.setRestaurant(restaurant);
        update(vote);
    }

    public List<Vote> findByUser(int userId) {
        return repository.getFilteredByUserWithRestaurant(userId);
    }

    public List<Vote> findByDate(LocalDate date) {
        return repository.getFilteredByDateWithRestaurant(date);
    }

    public Optional<Vote> findByUserAndDate(int userId, LocalDate date) {
        return repository.getFilteredByUserAndDateWithRestaurant(userId, date);
    }

    public Vote getTodaysVote(int userId) {
        return findByUserAndDate(userId, getCurrentDateTime().toLocalDate())
                .orElseThrow(() -> new NotFoundException("Today's vote for user is not found"));
    }

    public void delete(int id, int userId) {
        Vote entity = get(id, userId);
        validateOperation(entity, CrudOperation.DELETE);
        repository.delete(entity);
    }

    public LocalTime getEndVotingTime() {
        return endVotingTime;
    }

    public void revoke(int userId) {
        Optional<Vote> result = findByUserAndDate(userId, getCurrentDate());
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
            get(vote.getId(), vote.getUser().getId()); // assert vote belongs to user
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

    private LocalDate getCurrentDate() {
        return LocalDate.now(clockHolder.getClock());
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDateTime().toLocalDate());
    }

    private Restaurant getRestaurant(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id = " + id + " is not found"));
    }

    private User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id = " + id + " is not found"));
    }
}
