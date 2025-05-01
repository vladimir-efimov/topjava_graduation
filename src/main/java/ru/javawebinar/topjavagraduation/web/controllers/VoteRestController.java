package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.VoteService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjavagraduation.web.security.SecurityUtil.getAuthorizedUserId;


@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    public static final String REST_URL = "/rest/votes";
    private final VoteService service;

    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getTodays() {
        return service.findByDate(LocalDate.now());
    }

    @GetMapping("/find")
    public List<Vote> find(@Nullable @RequestParam LocalDate date) {
        int userId = getAuthorizedUserId();
        if (date != null) {
            Optional<Vote> result = service.findByUserAndDate(userId, date);
            return result.isPresent() ? List.of(result.get()) : List.of();
        } else {
            return service.findByUser(userId);
        }
    }

    @PutMapping(value = "/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@RequestParam int restaurantId) {
        service.vote(getAuthorizedUserId(), restaurantId);
    }

    @PutMapping(value = "/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke() {
        service.revoke(getAuthorizedUserId());
    }

    @GetMapping("/end-voting-time")
    public LocalTime getEndVotingTime() {
        return service.getEndVotingTime();
    }
}
