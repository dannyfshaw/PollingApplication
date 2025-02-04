package com.dizplai.polling.pollingservice;

import com.dizplai.polling.pollingservice.controller.PollController;
import com.dizplai.polling.pollingservice.model.Poll;
import com.dizplai.polling.pollingservice.repository.PollRepository;
import com.dizplai.polling.pollingservice.service.PollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PollingAppTests {

    private MockMvc mockMvc;

    @Mock
    private PollService pollService;

    @InjectMocks
    private PollController pollController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pollController).build();
    }

    @Test
    void testCreatePoll() throws Exception {
        Poll poll = new Poll("Favourite Team?", List.of("Liverpool", "Arsenal", "Leeds"));
        when(pollService.createPoll(any(Poll.class))).thenReturn(poll);

        mockMvc.perform(post("/polls/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"question\":\"Favourite Team?\",\"options\":[\"Liverpool\",\"Arsenal\",\"Leeds\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is("Favourite Team?")));
    }

    @Test
    void testGetActivePoll() throws Exception {
        Poll poll = new Poll("Favourite Colour?", List.of(ApplicationConstants.RED_STRING, ApplicationConstants.BLUE_STRING, ApplicationConstants.GREEN_STRING));
        when(pollService.getActivePoll()).thenReturn(poll);

        mockMvc.perform(get("/polls/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is(ApplicationConstants.POLL_TITLE)));
    }

    @Test
    void testVote() throws Exception {
        Poll poll = new Poll("Favourite Colour?", List.of(ApplicationConstants.RED_STRING, ApplicationConstants.BLUE_STRING, ApplicationConstants.GREEN_STRING));
        when(pollService.getActivePoll()).thenReturn(poll);
        when(pollService.vote(anyInt())).thenReturn(Map.of(ApplicationConstants.BLUE_STRING, 50.0));

        mockMvc.perform(post("/polls/vote?optionIndex=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Blue", is(50.0)));

        verify(pollService, times(1)).vote(anyInt());
    }
}
