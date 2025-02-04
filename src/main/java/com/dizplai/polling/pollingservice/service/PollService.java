package com.dizplai.polling.pollingservice.service;

import com.dizplai.polling.pollingservice.ApplicationConstants;
import com.dizplai.polling.pollingservice.model.Poll;
import com.dizplai.polling.pollingservice.repository.PollRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public Poll getActivePoll() {
        return pollRepository.findFirstByOrderByIdDesc()
                .orElseGet(() -> pollRepository.save(new Poll(ApplicationConstants.POLL_TITLE,
                        List.of(ApplicationConstants.RED_STRING, ApplicationConstants.BLUE_STRING, ApplicationConstants.GREEN_STRING))));
    }

    public Map<String, Double> vote(int optionIndex) {
        Poll poll = getActivePoll();
        if (optionIndex < 0 || optionIndex >= poll.getOptions().size()) {
            throw new IllegalArgumentException("Invalid option index");
        }
        log.info("Voting for option index: {}", optionIndex);
        poll.vote(optionIndex);
        pollRepository.save(poll);
        return poll.getResults();
    }
}