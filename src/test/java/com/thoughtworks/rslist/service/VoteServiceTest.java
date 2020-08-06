package com.thoughtworks.rslist.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoteServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    User user;
    RsEvent rsEvent;
    @BeforeEach
    void setup () {
        user = new User("wjl", "male", 18, "abc@aa.com", "12233334444");
        rsEvent = new RsEvent("one event start", "event", 1);
    }

    @Test
    public void should_vote_success_when_post_vote_given_voteNum_less_than_user_num() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        rsEvent = new RsEvent("one event start", "event", 1);
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        String jsonString1 = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "1"));
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        Vote vote = Vote.builder().userId(1).voteNum(4).voteTime("sadfasd").build();
        mockMvc.perform(post("/rs/vote/2").content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_vote_fail_when_post_vote_given_voteNum_more_than_user_num() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        rsEvent = new RsEvent("one event start", "event", 1);
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        String jsonString1 = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "1"));
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        Vote vote = Vote.builder().userId(1).voteNum(11).voteTime("sadfasd").build();
        mockMvc.perform(post("/rs/vote/2").content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}