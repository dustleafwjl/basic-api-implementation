package com.thoughtworks.rslist.domain;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    @NotNull
    private User user;

    public RsEvent() {

    }

    public RsEvent(String eventName, String keyWord, User user) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RsEvent rsEvent = (RsEvent) o;
        return Objects.equals(eventName, rsEvent.eventName) &&
                Objects.equals(user, rsEvent.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, user);
    }
}
