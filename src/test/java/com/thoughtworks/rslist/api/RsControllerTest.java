package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_get_rsevent_when_getRsEvent_given_index() throws Exception {
        mockMvc.perform(get("/rs/list/1")).andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rsevent_list_when_getRsEventLimit_given_limit() throws Exception {
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
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"无标签\",\"user\": {\"userName\":\"wjl123123123l\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(jsonPath("$.error", is("invalid request param")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_add_rsEvent_when_addRsEvent_given_new_rsEvent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        String jsonString = objectMapper.writeValueAsString(new RsEvent("猪肉涨价了", "经济", new User("wjl", "male", 19, "wjl@demo.com", "12233334444")));
        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list/4"))
                .andExpect(jsonPath("$.eventName").value("猪肉涨价了"))
                .andExpect(jsonPath("$.keyWord").value("经济"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(0)
    public void should_add_new_user_when_addRsEvent_given_new_rsEvent_and_new_user() throws Exception {
        String newRsEventStr = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"user\": {\"userName\":\"wjll\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_bad_request_phone_when_addRsEvent_given_eventName_is_null() throws Exception {
        String newRsEventStr = "{\"keyWord\":\"经济\",\"user\": {\"userName\":\"wjl\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_return_bad_request_phone_when_addRsEvent_given_keyWord_is_null() throws Exception {
        String newRsEventStr = "{\"eventName\":\"猪肉涨价了\",\"user\": {\"userName\":\"wjll\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
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
    public void should_update_when_updateRsEvent_given() throws Exception {
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"无标签\",\"user\": {\"userName\":\"wjll\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(put("/rs/event/1").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName").value("事件更改了"))
                .andExpect(jsonPath("$.keyWord").value("无标签"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_delete_when_removeRsEvent_given_index() throws Exception {
        mockMvc.perform(delete("/rs/event/3"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_error_when_addRsEvent_given_wrong_params() throws Exception {
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"无标签\",\"user\": {\"userName\":\"wjl123123123l\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error", is("invalid param")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_getRsEvent_given_wrong_index() throws Exception {
        String newRsEventStr = "{\"eventName\":\"事件更改了\",\"keyWord\":\"无标签\",\"user\": {\"userName\":\"wjl123123123l\",\"age\": 19,\"gender\": \"male\",\"email\": \"a@b.com\",\"phone\": \"18888888888\"}}";
        mockMvc.perform(get("/rs/list/0"))
                .andExpect(jsonPath("$.error", is("invalid index")))
                .andExpect(status().isBadRequest());
    }
}