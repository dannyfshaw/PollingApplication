package com.dizplai.polling.pollingservice.repository;

import com.dizplai.polling.pollingservice.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {
    Optional<Poll> findFirstByOrderByIdDesc();
}