package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.rslist.dto.RsEventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;


public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    @NotNull
    private int userId;

    public RsEvent() {

    }

    public RsEvent(String eventName, String keyWord, int userId) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RsEvent rsEvent = (RsEvent) o;
        return Objects.equals(eventName, rsEvent.eventName) &&
                Objects.equals(userId, rsEvent.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, userId);
    }

    public RsEventDto rsEventToRsEventDto() {
//        return RsEventDto.builder().eventName(this.eventName).keyWord(this.keyWord).userDto()
        return null;
    }
}
