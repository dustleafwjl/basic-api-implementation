package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_get_rsevent_when_getRsEvent_given_index() throws Exception {
        mockMvc.perform(get("/rs/list/1")).andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rsevent_list_when_getRsEventLimit_given_limit() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_add_rsEvent_when_addRsEvent_given_new_rsEvent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String newRsEventStr = objectMapper.writeValueAsString(new RsEvent("猪肉涨价了", "经济"));
        mockMvc.perform(post("/rs/event").content(newRsEventStr).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list/4"))
                .andExpect(jsonPath("$.eventName").value("猪肉涨价了"))
                .andExpect(jsonPath("$.keyWord").value("经济"))
                .andExpect(status().isOk());
    }
}