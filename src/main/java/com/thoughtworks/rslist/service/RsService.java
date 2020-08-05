package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.exception.RsEventNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RsService {
    static private RsService Inst;
    private List<RsEvent> rsList;
    private List<User> userList;
    static public RsService getInstance() {
        if(Inst == null) {
            Inst = new RsService();
        }
        return Inst;
    }
    public RsService() {
        this.rsList = initRsEventList();
        this.userList = initRsUserList();
    }
    private List<RsEvent> initRsEventList() {
        List<RsEvent> rsEvents = new ArrayList<>();
        rsEvents.add(new RsEvent("第一条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));
        rsEvents.add(new RsEvent("第二条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));
        rsEvents.add(new RsEvent("第三条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));

        return rsEvents;
    }
    private List<User> initRsUserList() {
        return UserList.userList;
    }
    public RsEvent getRsEvent(int index) {
        if(index <= 0 || index > rsList.size()) {
            throw new RsEventNotValidException("invalid index");
        }
        return rsList.get(index - 1);
    }

    public List<RsEvent> getAllRsList() {
        return rsList;
    }

    public List<RsEvent> getRsListForLimit(int start, int end) {
        if( start<=0 || start > end || end > rsList.size()) {
            throw new RsEventNotValidException("invalid request param");
        }
        return rsList.subList(start - 1, end);
    }

    public void addRsEvent(RsEvent rsEvent) {
        User user = rsEvent.getUser();
        boolean userIsNotExist = userList.stream().noneMatch(ele -> ele.equals(user));
        if(userIsNotExist) {
            userList.add(user);
        }
        rsList.add(rsEvent);
    }
    public void removeRsEvent(int index) {
        if(index <= 0 || index > rsList.size()) {
            throw new RsEventNotValidException("invalid request param");
        }
        rsList.remove(index - 1);
    }
    public void updateRsEvent(int index, RsEvent rsEvent) {
        rsList.get(index - 1).setEventName(rsEvent.getEventName());
        rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    }
}
