package ru.javawebinar.topjavagraduation.service;

import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.repository.VoteRepository;

import java.util.Date;
import java.util.List;

public class VoteService extends AbstractBaseEntityService<Vote> {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Vote> findByUser(int id) {
        return repository.findByUser(id);
    }

    public List<Vote> findDate(Date date) {
        return repository.findByDate(date);
    }
}
