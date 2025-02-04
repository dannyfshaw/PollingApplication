package com.dizplai.polling.pollingservice.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @ElementCollection
    private List<String> options;
    @ElementCollection
    private List<Integer> votes;

    public Poll() {
    }

    public Poll(String question, List<String> options) {
        this.question = question;
        this.options = options;
        this.votes = new ArrayList<>(Collections.nCopies(options.size(), 0));
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void vote(int optionIndex) {
        votes.set(optionIndex, votes.get(optionIndex) + 1);
    }

    public Map<String, Double> getResults() {
        int totalVotes = votes.stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> results = new LinkedHashMap<>();
        for (int i = 0; i < options.size(); i++) {
            double percentage = totalVotes == 0 ? 0 : (votes.get(i) * 100.0 / totalVotes);
            results.put(options.get(i), percentage);
        }
        return results;
    }
}