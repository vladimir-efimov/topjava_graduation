package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.RestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        if(!vote.getUser().getId().equals(userId)) {
            throw new NotFoundException("Attempt to access not owned vote");
        }
        return vote;
    }

    //todo: service should set actual voting date

    public List<Vote> findByUser(int userId) {
        return repository.findByUser(userId);
    }

    public List<Vote> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public Restaurant getElected() {
        if (getCurrentDateTime().getHour() < END_VOTE_HOURS) {
            throw new IllegalStateException("Voting is in progress. Call this method after " + END_VOTE_HOURS + " hours");
        }
        List<Vote> votes = findByDate(getCurrentDateTime().toLocalDate());
        if (votes.isEmpty()) {
            throw new NotFoundException("No votes found for today");
        }
        var groupedVotes = votes.stream()
                .collect(Collectors.groupingBy(v -> v.getRestaurant().getId(), Collectors.counting()))
                .entrySet();
        int restaurantId = groupedVotes.stream()
                .max(Comparator.comparingLong(entry -> entry.getValue())).get().getKey();
        return restaurantRepository.get(restaurantId);
    }

    @Override
    protected void validateOnCreate(Vote vote) {
        super.validateOnCreate(vote);
        assertVoteOperationIsAllowed(vote);
    }

    @Override
    protected void validateOnUpdate(Vote vote) {
        super.validateOnUpdate(vote);
        assertVoteOperationIsAllowed(vote);
    }

    @Override
    protected void validateOnDelete(Vote vote) {
        super.validateOnDelete(vote);
        assertVoteOperationIsAllowed(vote);
    }

    private void assertVoteOperationIsAllowed(Vote vote) {
        if (getCurrentDateTime().getHour() >= END_VOTE_HOURS) {
            throw new IllegalStateException("Operations with vote are not allowed after " + END_VOTE_HOURS + " hours");
        }
        if(vote.getDate() == null) {
            throw new IllegalArgumentException("Vote should has not null date");
        }
        if(beforeToday(vote.getDate())) {
            throw new IllegalStateException("Can't operate with date in the past");
        }
    }

    void setDateTime(LocalDateTime date) {
        testingPurposeDate = date;
    }

    static int getEndVoteHours() {
        return END_VOTE_HOURS;
    }

    private LocalDateTime getCurrentDateTime() {
        if(testingPurposeDate == null) {
            return LocalDateTime.now();
        }
        return testingPurposeDate;
    }

    private boolean beforeToday(LocalDate date) {
        return date.isBefore(getCurrentDateTime().toLocalDate());
    }
}
