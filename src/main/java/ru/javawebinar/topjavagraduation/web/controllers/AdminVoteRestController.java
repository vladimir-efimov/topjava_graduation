package ru.javawebinar.topjavagraduation.web.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.VoteService;
import ru.javawebinar.topjavagraduation.to.VoteTo;


@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController {
    public static final String REST_URL = "/rest/admin/votes";
    private final VoteService service;

    public AdminVoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getAll() {
        return service.getAll().stream().map(this::convertEntity).toList();
    }

    @GetMapping("/find")
    public List<Vote> find(@Nullable @RequestParam("user_id") Integer userId,
                           @Nullable @RequestParam("date") LocalDate date) {
        if(userId != null) {
            if (date != null) {
                Optional<Vote> result = service.findByUserAndDate(userId, date);
                return result.isPresent() ? List.of(result.get()) : List.of();
            } else {
                return service.findByUser(userId);
            }
        } else {
            return date != null ? service.findByDate(date) : service.getAll();
        }
    }

    @GetMapping("/elected")
    public Restaurant getElected() {
        return service.getElected();
    }

    @GetMapping("/end_voting_time")
    public LocalTime getEndVotingTime() {
        return service.getEndVotingTime();
    }

    @PutMapping("/end_voting_time")
    public void setEndVotingTime(@RequestParam("time") LocalTime endVotingTime) {
        service.setEndVotingTime(endVotingTime);
    }

    private VoteTo convertEntity(Vote vote) {
        return new VoteTo(vote.getDate(), vote.getUser().getId(), vote.getRestaurant().getId());
    }
}
