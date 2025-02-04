package com.dizplai.polling.pollingservice.controller;

import com.dizplai.polling.pollingservice.model.Poll;
import com.dizplai.polling.pollingservice.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/polls")
public class PollController {
    @Autowired
    private PollService pollService;

    @PostMapping("/create")
    public Poll createPoll(@RequestBody Poll poll) {
        return pollService.createPoll(poll);
    }

    @GetMapping("/active")
    public Poll getActivePoll() {
        return pollService.getActivePoll();
    }

    @PostMapping("/vote")
    public Map<String, Double> vote(@RequestParam int optionIndex) {
        log.info("Received vote request for option index: {}", optionIndex);
        return pollService.vote(optionIndex);
    }
}