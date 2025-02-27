package ru.javawebinar.topjavagraduation.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.model.User;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.service.VoteService;
import ru.javawebinar.topjavagraduation.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteRestController.REST_URL , produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController extends AbstractBaseEntityRestController<Vote, VoteTo> {

    public static final String REST_URL = "/rest/votes";
    private final VoteService service;

    public VoteRestController(VoteService service) {
        super(service, REST_URL);
        this.service = service;
    }

    @Override
    public Vote convertTo(VoteTo voteTo) {
        var restaurant = new Restaurant();
        restaurant.setId(voteTo.getRestaurantId());
        var user = new User();
        user.setId(voteTo.getUserId());
        return new Vote(user, restaurant);
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Vote vote = service.get(id);
        service.delete(id, vote.getUser().getId());
    }

}
