package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}