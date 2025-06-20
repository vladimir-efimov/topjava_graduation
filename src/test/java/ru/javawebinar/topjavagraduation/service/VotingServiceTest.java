package ru.javawebinar.topjavagraduation.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.data.TestData;
import ru.javawebinar.topjavagraduation.data.TestDataProvider;
import ru.javawebinar.topjavagraduation.model.Vote;
import ru.javawebinar.topjavagraduation.exception.IllegalOperationException;


public class VotingServiceTest extends AbstractServiceTest<Vote> {

    private final VoteService service;
    private final TestClockHolder clockHolder;

    public VotingServiceTest(@Autowired VoteService service, @Autowired TestDataProvider<Vote> dataProvider,
                             @Autowired TestClockHolder clockHolder) {
        super(service, dataProvider);
        this.service = service;
        this.clockHolder = clockHolder;
        clockHolder.setDateTime(TestData.date.atStartOfDay());
    }

    @AfterEach
    void restoreClockHolder() {
        clockHolder.setDateTime(LocalDateTime.now());
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
    void updateVote() {
        LocalDateTime dateTime = TestData.date.atStartOfDay().plusHours(service.getEndVotingTime().getHour() - 1);
        clockHolder.setDateTime(dateTime); // update vote is not allowed now
        Integer voteId = TestData.updatedVote.getId();
        service.update(TestData.updatedVote);
        dataProvider.getMatcher().assertMatch(TestData.updatedVote, service.get(voteId));
    }

    @Test
    void tryUpdateVote() {
        Vote registeredVote = service.create(TestData.newVote);
        LocalDateTime tooLateTime = TestData.date.atStartOfDay().plusHours(service.getEndVotingTime().getHour() + 1);
        clockHolder.setDateTime(tooLateTime); // update vote is not allowed now
        registeredVote.setRestaurant(TestData.restaurants[2]);
        assertThrows(IllegalOperationException.class, () -> service.update(registeredVote));
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
