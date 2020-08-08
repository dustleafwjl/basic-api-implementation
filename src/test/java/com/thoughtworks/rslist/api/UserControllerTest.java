package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    UserDto userDto;
    @BeforeEach
    void setup() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();

        userDto =
                UserDto.builder()
                        .voteNum(10)
                        .phone("188888888888")
                        .gender("female")
                        .email("a@b.com")
                        .age(19)
                        .userName("idolice")
                        .build();
        userDto = userRepository.save(userDto);
    }

    @Test
    public void should_get_all_user_when_getUsers_given_request() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_set_user_when_register_given_info() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_bad_request_when_register_given_name_more_then_8() throws Exception {
        User user = new User("wjlddddddddd", "male", 18, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_bad_request_when_register_given_age_less_than_18_more_than_100() throws Exception {
        User user = new User("wjl", "male", 17, "jianlin@qq.com", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_bad_request_email_when_register_given_bad_eamil() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin", "17733334444");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_return_bad_request_when_register_given_phone_num_not_11() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin", "177333344443");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        user.setPhone("12233");
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_phone_when_register_given_phone_is_not_begin_one() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin", "177333344443");
        String stringJson = new ObjectMapper().writeValueAsString(user);
        user.setPhone("21122223333");
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_error_invalid_user_when_register_given_wrong_params() throws Exception {
        User user = new User("wjl", "male", 18, "jianlin", "177333344443");
        String stringJson = new ObjectMapper().writeValueAsString(user);

        user.setPhone("21122223333");
        mockMvc.perform(post("/user").content(stringJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid user")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_get_user_when_getUserById_given_userId() throws Exception {
        mockMvc.perform(get("/user/"+userDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_name", is(userDto.getUserName())));
    }

    @Test
    public void should_remove_user_and_all_rsEvent_when_removeUserById_given_userId() throws Exception {
        RsEventDto rsEventDtofirst = RsEventDto.builder().eventName("第一条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtofirst = rsEventRepository.save(rsEventDtofirst);
        RsEventDto rsEventDtoSecond = RsEventDto.builder().eventName("第二条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtoSecond = rsEventRepository.save(rsEventDtoSecond);
        RsEventDto rsEventDtoThird = RsEventDto.builder().eventName("第三条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtoThird = rsEventRepository.save(rsEventDtoThird);
        mockMvc.perform(delete("/user/"+userDto.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/user/"+userDto.getId()))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/rs/list/"+rsEventDtofirst.getId()))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/rs/list/"+rsEventDtoSecond.getId()))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/rs/list/"+rsEventDtoThird.getId()))
                .andExpect(status().isBadRequest());
    }
}