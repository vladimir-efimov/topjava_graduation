package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;

import java.util.Date;
import java.util.List;

public class VoteService extends AbstractBaseEntityService<Vote> {

    private final static int END_VOTE_HOURS = 11;
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Vote> findByUser(int id) {
        return repository.findByUser(id);
    }

    public List<Vote> findByDate(Date date) {
        return repository.findByDate(date);
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
        Date currentDate = getCurrentDate();
        if (currentDate.getHours() >= END_VOTE_HOURS) {
            throw new IllegalStateException("Operations with vote are not allowed after " + END_VOTE_HOURS + " hours");
        }
        if(vote.getDate() == null) {
            throw new IllegalArgumentException("Vote should has not null date");
        }
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        if(vote.getDate().before(currentDate)) {
            throw new IllegalStateException("Can't operate with date in the past");
        }
    }

    // todo: need some way to overwrite current date in tests
    Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
