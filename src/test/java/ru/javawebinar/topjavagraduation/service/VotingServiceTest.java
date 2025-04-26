package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.validation.exception.IllegalOperationException;


public class VotingServiceTest extends AbstractServiceTest<Vote> {

    private final VoteService service;

    public VotingServiceTest(@Autowired VoteService service, @Autowired TestDataProvider<Vote> dataProvider) {
        super(service, dataProvider);
        this.service = service;
        service.setDateTime(TestData.date.atStartOfDay());
    }

    @Test
    void delete() {
        Vote vote = dataProvider.getFirst();
        assertTrue(service.getAll().contains(vote));
        service.delete(vote.getId(), vote.getUser().getId());
        assertFalse(service.getAll().contains(vote));
    }

    @Test
    void tryUpdateNotOwnVote() {
        Vote vote = dataProvider.getFirst();
        assertNotEquals(TestData.users[3], vote.getUser());
        // correct setting of user is responsibility of controller,
        // but user can send incorrect id of vote
        Vote corruptedVote = new Vote(vote.getId(), TestData.date, TestData.users[3], TestData.restaurants[0]);
        assertThrows(SecurityException.class, () -> service.update(corruptedVote));
    }

    @Test
    void tryVoteAfterVotingIsCompleted() {
        Vote vote = new Vote(TestData.users[2], TestData.restaurants[3]);
        LocalDateTime dateTime = TestData.date.atStartOfDay().plusHours(service.getEndVotingTime().getHour() + 1);
        service.setDateTime(dateTime);
        assertThrows(IllegalOperationException.class, () -> service.create(vote));
    }

    @Test
    void getNotOwned() {
        assertThrows(SecurityException.class, () -> service.get(dataProvider.getFirst().getId(), 999));
    }

    @Test
    void findByDate() {
        List<Vote> votes = dataProvider.getAll();
        List<Vote> foundVotes = service.findByDate(TestData.date);
        assertEquals(votes.size(), foundVotes.size());
        for (int i = 0; i < foundVotes.size(); i++) {
            assertEquals(foundVotes.get(i), votes.get(i));
        }
        assertTrue(service.findByDate(LocalDate.EPOCH).isEmpty());
    }
}
