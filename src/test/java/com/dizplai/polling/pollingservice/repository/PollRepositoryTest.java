package com.dizplai.polling.pollingservice.repository;

import com.dizplai.polling.pollingservice.ApplicationConstants;
import com.dizplai.polling.pollingservice.model.Poll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PollRepositoryTest {
    @Autowired
    private PollRepository pollRepository;

    @Test
    void testSaveAndRetrievePoll() {
        Poll poll = new Poll(ApplicationConstants.POLL_TITLE,
                List.of(ApplicationConstants.RED_STRING, ApplicationConstants.BLUE_STRING, ApplicationConstants.GREEN_STRING));
        poll.vote(ApplicationConstants.RED_INDEX);
        Poll savedPoll = pollRepository.save(poll);

        Optional<Poll> retrievedPoll = pollRepository.findFirstByOrderByIdDesc();
        assertThat(retrievedPoll).isPresent();
        assertThat(retrievedPoll.get().getQuestion()).isEqualTo(ApplicationConstants.POLL_TITLE);
    }
}