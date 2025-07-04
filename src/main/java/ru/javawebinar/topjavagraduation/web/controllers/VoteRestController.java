package ru.javawebinar.topjavagraduation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.VoteService;
import ru.javawebinar.topjavagraduation.to.VoteTo;
import ru.javawebinar.topjavagraduation.utils.ConverterUtils;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjavagraduation.utils.ConverterUtils.convertVote;
import static ru.javawebinar.topjavagraduation.web.security.SecurityUtil.getAuthorizedUserId;


@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    public static final String REST_URL = "/rest/votes";
    @Autowired
    private VoteService service;

    @GetMapping
    public List<VoteTo> filter(@Nullable @RequestParam LocalDate date) {
        int userId = getAuthorizedUserId();
        if (date != null) {
            Optional<Vote> result = service.findByUserAndDate(userId, date);
            return result.isPresent() ? List.of(convertVote(result.get())) : List.of();
        } else {
            return service.findByUser(userId).stream().map(ConverterUtils::convertVote).toList();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<VoteTo> createWithLocation(@RequestParam int restaurantId) {
        Vote vote = service.create(getAuthorizedUserId(), restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/todays-vote}").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(convertVote(vote));
    }

    @GetMapping("/todays-vote")
    public VoteTo getTodaysVote() {
        return convertVote(service.getTodaysVote(getAuthorizedUserId()));
    }

    @PutMapping(value = "/todays-vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId) {
        service.update(getAuthorizedUserId(), restaurantId);
    }

    @DeleteMapping(value = "/todays-vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void revoke() {
        service.revoke(getAuthorizedUserId());
    }

    @GetMapping("/end-voting-time")
    public LocalTime getEndVotingTime() {
        return service.getEndVotingTime();
    }
}
