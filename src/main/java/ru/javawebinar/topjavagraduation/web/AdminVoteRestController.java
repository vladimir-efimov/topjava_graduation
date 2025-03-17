package ru.javawebinar.topjavagraduation.web;

import java.time.LocalTime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.service.VoteService;


@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController {
    public static final String REST_URL = "/rest/admin/votes";
    private final VoteService service;

    public AdminVoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping("/end_voting_time")
    public LocalTime getEndVotingTime() {
        return service.getEndVotingTime();
    }

    @PutMapping("/end_voting_time")
    public void setEndVotingTime(@RequestParam("time") LocalTime endVotingTime) {
        service.setEndVotingTime(endVotingTime);
    }
}
