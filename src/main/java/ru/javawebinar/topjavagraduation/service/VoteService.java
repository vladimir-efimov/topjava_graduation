package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;
import ru.javawebinar.topjavagraduation.validation.exception.RepositoryOperationException;

@Service
public class VoteService extends AbstractBaseEntityService<Vote> {

    private final static int END_VOTE_HOURS = 11;
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private LocalDateTime testingPurposeDate = null;

    public VoteService(VoteRepository repository, RestaurantRepository restaurantRepository) {
        super(repository);
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
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
        Vote modifiedVote = new Vote(null, getCurrentDateTime().toLocalDate(), vote.getUser(), vote.getRestaurant());
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

    public Restaurant getElected() {
        if (getCurrentDateTime().getHour() < END_VOTE_HOURS) {
            throw new IllegalOperationException("Voting is in progress. Call this method after " + END_VOTE_HOURS + " hours");
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

    @Override
    protected void validateOperation(Vote vote, CrudOperation operation) {
        super.validateOperation(vote, operation);

        if (operation == CrudOperation.UPDATE || operation == CrudOperation.DELETE) {
            get(vote.getId(), vote.getUser().getId()); // assert is own
        }
        if (getCurrentDateTime().getHour() >= END_VOTE_HOURS) {
            throw new IllegalOperationException("Operations with vote are not allowed after " + END_VOTE_HOURS + " hours");
        }
        if (beforeToday(vote.getDate())) {
            throw new IllegalOperationException("Can't operate with date in the past");
        }
    }

    public void delete(int id, int userId) {
        Vote entity = get(id, userId);
        validateOperation(entity, CrudOperation.DELETE);
        if (!repository.delete(id)) {
            throw new RepositoryOperationException("Can't delete " + entity.getClass().getSimpleName() + " with id " + id);
        }
    }

    void setDateTime(LocalDateTime date) {
        testingPurposeDate = date;
    }

    static int getEndVoteHours() {
        return END_VOTE_HOURS;
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
