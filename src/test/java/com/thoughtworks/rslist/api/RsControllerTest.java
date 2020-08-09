package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    UserDto userDto;

    RsEvent rsEvent;
    User user;

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

        rsEvent = new RsEvent("one event start", "event", 1);
        user = new User("wjl", "male", 18, "abssc@aa.com", "12233334444");
    }

    @Test
    public void should_get_rsevent_when_getRsEvent_given_index() throws Exception {
        RsEventDto rsEventDtofirst = RsEventDto.builder().eventName("第一条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtofirst = rsEventRepository.save(rsEventDtofirst);
        mockMvc.perform(get("/rs/list/"+rsEventDtofirst.getId()))
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$.keyWord").value("无标签"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rsevent_without_user_when_getRsEvent_given_index() throws Exception {
        RsEventDto rsEventDtofirst = RsEventDto.builder().eventName("第一条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtofirst = rsEventRepository.save(rsEventDtofirst);
        mockMvc.perform(get("/rs/list/"+rsEventDtofirst.getId()))
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rsevent_list_when_getRsEventLimit_given_limit() throws Exception {
        RsEventDto rsEventDtofirst = RsEventDto.builder().eventName("第一条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtofirst = rsEventRepository.save(rsEventDtofirst);
        RsEventDto rsEventDtoSecond = RsEventDto.builder().eventName("第二条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtoSecond = rsEventRepository.save(rsEventDtoSecond);
        RsEventDto rsEventDtoThird = RsEventDto.builder().eventName("第三条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtoThird = rsEventRepository.save(rsEventDtoThird);
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_error_invalid_request_param_when_getRsEvent_given_wrong_limit() throws Exception {
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"无标签\",\"user\": "+ userDto.getId() +"}";
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_add_rsEvent_when_addRsEvent_given_new_rsEvent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
//        String jsonString = objectMapper.writeValueAsString(new RsEvent("猪肉涨价了", "经济", new User("wjl", "male", 19, "wjl@demo.com", "12233334444")));
        String newRsEventStr = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\": "+ userDto.getId() +"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_bad_request_phone_when_addRsEvent_given_eventName_is_null() throws Exception {
        String newRsEventStr = "{\"keyWord\":\"经济\",\"userId\": "+ userDto.getId() +"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_phone_when_addRsEvent_given_keyWord_is_null() throws Exception {
        String newRsEventStr = "{\"eventName\":\"猪肉涨价了\",\"userId\": "+ userDto.getId() +"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_phone_when_addRsEvent_given_user_is_null() throws Exception {
        String newRsEventStr = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_addRsEvent_given_wrong_params() throws Exception {
        String newRsEventStr = "{\"eventaame\":\"事件更改了\",\"keyWord\":\"无标签\"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid param")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_getRsEvent_given_wrong_index() throws Exception {
        mockMvc.perform(get("/rs/list/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_response_when_addRsEvent_given_user_is_not_exist() throws Exception {
        String newRsEventStr = "{\"keyWord\":\"经济\",\"userId\": "+ 999 +"}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_remove_rsevent_when_removeRsEvent_given_rsevent_id() throws Exception {
        RsEventDto rsEventDtofirst = RsEventDto.builder().eventName("第一条事件").keyWord("无标签").userDto(userDto).voteNum(2).build();
        rsEventDtofirst = rsEventRepository.save(rsEventDtofirst);
        mockMvc.perform(delete("/rs/event/" + rsEventDtofirst.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/" + rsEventDtofirst.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_update_when_patch_updateRsEvent_given_id_and_rsEvent() throws Exception {
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"经济\",\"userId\": "+ userDto.getId() +"}";
        RsEventDto rsEventDto =
                RsEventDto.builder().keyWord("无分类").eventName("第一条事件").userDto(userDto).build();
        rsEventDto = rsEventRepository.save(rsEventDto);
        mockMvc.perform(patch("/rs/event/"+rsEventDto.getId()).content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/"+rsEventDto.getId()))
                .andExpect(jsonPath("$.eventName").value("事件更改了"))
                .andExpect(jsonPath("$.keyWord").value("经济"))
                .andExpect(status().isOk());
    }
}