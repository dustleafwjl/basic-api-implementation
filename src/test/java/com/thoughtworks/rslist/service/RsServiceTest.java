package com.thoughtworks.rslist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsServiceTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    User user;
    RsEvent rsEvent;

    @BeforeEach
    void setup () {
        rsEvent = new RsEvent("one event start", "event", 1);
        user = new User("wjl", "male", 18, "abssc@aa.com", "12233334444");
    }

    @Test
    void should_add_reEvent_when_addRsEvent_given_params() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);
        String jsonString1 = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_bad_response_when_addRsEvent_given_user_has_exist() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        rsEvent = new RsEvent("one event start", "event", 999);
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        String jsonString1 = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "1"));
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_remove_rsevent_when_removeRsEvent_given_rsevent_name() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        String jsonString1 = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "1"));
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/rs/event/2").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/2"))
                .andExpect(status().isBadRequest());
    }
}