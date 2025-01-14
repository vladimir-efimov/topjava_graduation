package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.javawebinar.topjavagraduation.model.Restaurant;
import ru.javawebinar.topjavagraduation.repository.InMemoryRestaurantRepository;
import ru.javawebinar.topjavagraduation.repository.InMemoryVoteRepository;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.validation.exception.NotFoundException;


public class VotingServiceTest {

    VoteService service;
    ArrayList<Vote> votes = new ArrayList<>();

    @BeforeEach
    void setup() {
        var restaurantRepository = new InMemoryRestaurantRepository();
        service = new VoteService(new InMemoryVoteRepository(), restaurantRepository);
        LocalDateTime dateTime = TestData.date.atStartOfDay();
        service.setDateTime(dateTime);
        for(Restaurant restaurant: TestData.restaurants) {
            restaurantRepository.save(new Restaurant(restaurant.getName(), restaurant.getAddress()));
        }
        votes.clear();
        for (Vote vote: TestData.votes) {
            votes.add(service.create(vote));
        }
    }

    @Test
    void get() {
        Vote vote = service.get(votes.getFirst().getId(), TestData.votes[0].getUser().getId());
        assertEquals(votes.getFirst(), vote);
        //todo: nice to compare with vote object from TestData
    }

    @Test
    void vote() {
        Vote vote = new Vote(TestData.date, TestData.users[2], TestData.restaurants[3]);
        Vote savedVote = service.create(vote);
        assertNotNull(savedVote.getId());
        assertEquals(TestData.date, savedVote.getDate());
        assertEquals(vote.getRestaurant(), savedVote.getRestaurant());
        assertEquals(vote.getUser(), savedVote.getUser());
    }

    @Test
    void tryVoteAfterVotingIsCompleted() {
        Vote vote = new Vote(TestData.date, TestData.users[2], TestData.restaurants[3]);
        LocalDateTime dateTime = TestData.date.atStartOfDay().plusHours(VoteService.getEndVoteHours() + 1);
        service.setDateTime(dateTime);
        assertThrows(IllegalStateException.class, () -> service.create(vote));
    }

    @Test
    void getNotOwned() {
        assertThrows(NotFoundException.class, () -> service.get(votes.getFirst().getId(), 999));
    }

    @Test
    void findByDate() {
        List<Vote> foundVotes = service.findByDate(TestData.date);
        assertEquals(votes.size(), foundVotes.size());
        for(int i=0; i<foundVotes.size(); i++) {
            assertEquals(foundVotes.get(i), votes.get(i));
        }
        assertTrue(service.findByDate(LocalDate.EPOCH).isEmpty());
    }

    @Test
    void tryGetElectedWhileVotingIsInProgress() {
        assertThrows(IllegalStateException.class, () -> service.getElected());
    }

    @Test
    void getElected() {
        LocalDateTime dateTime = TestData.date.atStartOfDay().plusHours(VoteService.getEndVoteHours() + 1);
        service.setDateTime(dateTime);
        Restaurant restaurant = service.getElected();
        assertEquals(TestData.popularRestaurant, restaurant);
    }
}
