package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_set_user_when_register_given_info() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_vaild_name_when_register_given_name_more_then_8() throws Exception {
        User user = new User("wjlddddddddd", "male", 18, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_vaild_age_when_register_given_age_less_than_18_more_than_100() throws Exception {
        User user = new User("wjl", "male", 17, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_vaild_email_when_register_given_bad_eamil() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



}