package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final static LocalTime DEFAULT_END_VOTE_TIME = LocalTime.of(11, 00);
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private LocalTime endVotingTime;
    private LocalDateTime testingPurposeDate = null;

    @Autowired
    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this(repository, restaurantRepository, userRepository, DEFAULT_END_VOTE_TIME);
    }

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository,
                       LocalTime endVotingTime) {
        super(repository);
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
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

    public Restaurant getElected() {
        if (getCurrentDateTime().toLocalTime().isBefore(endVotingTime)) {
            throw new IllegalOperationException("Voting is in progress. Call getElected() after " + endVotingTime);
        }
        List<Vote> votes = findByDate(getCurrentDateTime().toLocalDate());
        if (votes.isEmpty()) {
            throw new NotFoundException("No votes found for today");
        }
        var groupedVotes = votes.stream()
                .collect(Collectors.groupingBy(v -> v.getRestaurant().getId(), Collectors.counting()))
                .entrySet();
        int restaurantId = groupedVotes.stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)).get().getKey();
        return restaurantRepository.get(restaurantId);
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

    public void setEndVotingTime(LocalTime endVotingTime) {
        this.endVotingTime = endVotingTime;
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
            get(vote.getId(), vote.getUser().getId()); // assert is own
        }
        if (!getCurrentDateTime().toLocalTime().isBefore(endVotingTime)) {
            throw new IllegalOperationException("Operations with vote are not allowed after " + endVotingTime);
        }
        if (beforeToday(vote.getDate())) {
            throw new IllegalOperationException("Can't operate with date in the past");
        }
    }

    void setDateTime(LocalDateTime date) {
        testingPurposeDate = date;
    }

    private LocalDateTime getCurrentDateTime() {
        if (testingPurposeDate == null) {
            return LocalDateTime.now();
        }
        return testingPurposeDate;
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDateTime().toLocalDate());
    }
}
