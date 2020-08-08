package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoteControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    UserDto userDto;
    RsEventDto rsEventDto;

    RsEvent rsEvent;
    User user;

    @BeforeEach
    void setup () {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();

        userDto =
                UserDto.builder().voteNum(10).phone("188888888888")
                        .gender("female").email("a@b.com")
                        .age(19).userName("idolice").build();
        userDto = userRepository.save(userDto);

        rsEventDto = RsEventDto.builder()
                    .userDto(userDto).eventName("第一个事件").keyWord("无标签").build();
        rsEventDto = rsEventRepository.save(rsEventDto);
        rsEvent = new RsEvent("one event start", "event", 1);
        user = new User("wjl", "male", 18, "abssc@aa.com", "12233334444");
    }

    @Test
    public void should_vote_success_when_post_vote_given_voteNum_less_than_user_num() throws Exception {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        ObjectMapper objectMapper = new ObjectMapper();
        Vote vote = Vote.builder().userId(1).voteNum(4).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_vote_fail_when_post_vote_given_voteNum_more_than_user_num() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Vote vote = Vote.builder().userId(1).voteNum(11).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_get_vote_record_when_get_voteRecord_given_eventid_and_pageindex() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Vote vote = Vote.builder().userId(userDto.getId()).voteNum(4).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/vote").param("eventId", String.valueOf(rsEventDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId", is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())));
    }

    @Test
    public void should_get_vote_record_when_get_voteRecord_given_userid_and_pageindex() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Vote vote = Vote.builder().userId(userDto.getId()).voteNum(4).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/vote").param("userId", String.valueOf(userDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId", is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())));
    }

    @Test
    public void should_get_vote_record_when_get_voteRecord_given_userid_and_eventid_pageindex() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Vote vote = Vote.builder().userId(userDto.getId()).voteNum(4).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        vote.setVoteTime("2019-1");
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/vote")
                .param("eventId", String.valueOf(rsEventDto.getId()))
                .param("userId", String.valueOf(userDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventId", is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())));
    }

    @Test
    public void should_get_vote_record_when_get_voteRecord_given_time_limit_pageindex() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Vote vote = Vote.builder().userId(userDto.getId()).voteNum(4).voteTime(String.valueOf(now)).build();
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        vote.setVoteTime("2019-1");
        mockMvc.perform(post("/rs/vote/"+rsEventDto.getId())
                .content(objectMapper.writeValueAsString(vote)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/vote")
                .param("eventId", String.valueOf(rsEventDto.getId()))
                .param("userId", String.valueOf(userDto.getId()))
                .param("pageIndex", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventId", is(rsEventDto.getId())))
                .andExpect(jsonPath("$[0].userId", is(userDto.getId())));
    }
}