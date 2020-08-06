package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RsService {
    @Autowired
    RsEventRepository rsEventRepository;

    @Autowired
    UserRepository userRepository;

    private List<User> initRsUserList() {
        return UserList.userList;
    }
    public RsEvent getRsEvent(int index) {
        if(index <= 0 || index > rsEventRepository.count()) {
            throw new RsEventNotValidException("invalid index");
        }
        RsEventDto rsEventDto = rsEventRepository.findById(index).orElse(new RsEventDto());
        RsEvent rsEvent = rsEventDto.rsEventDtoToRsEvent();
        if(rsEvent.getEventName() == null) {
            return null;
        }
        return rsEvent;
    }

    public List<RsEvent> getAllRsList() {
        List<RsEventDto> rsEventDtos = rsEventRepository.findAll();
        List<RsEvent> rsEvents = rsEventDtos.stream().map(ele ->
                new RsEvent(ele.getEventName(), ele.getKeyWord(), ele.getUserDto().getId())).collect(Collectors.toList());
        return rsEvents;
    }

    public List<RsEvent> getRsListForLimit(int start, int end) {
        List<RsEventDto> rsEventDtos = rsEventRepository.findAll();
        if( start<=0 || start > end || end > rsEventDtos.size()) {
            throw new RsEventNotValidException("invalid request param");
        }
        List<RsEvent> rsEvents = rsEventDtos.stream().map(ele ->
                new RsEvent(ele.getEventName(), ele.getKeyWord(), ele.getUserDto().getId())).collect(Collectors.toList());
        return rsEvents.subList(start - 1, end);
    }

    public int addRsEvent(RsEvent rsEvent) {
        int userId = rsEvent.getUserId();
        boolean userIsExist = userRepository.existsById(userId);
        if(!userIsExist) {
            return -1;
        }
        UserDto userDto = userRepository.findById(rsEvent.getUserId()).orElse(null);
        rsEventRepository.save(RsEventDto.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWord()).userDto(userDto).build());
        return rsEventRepository.findAll().size();
    }
    public void removeRsEvent(int index) {
        rsEventRepository.deleteById(index);
    }
    public void updateRsEvent(int index, RsEvent rsEvent) {
//        rsList.get(index - 1).setEventName(rsEvent.getEventName());
//        rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    }
}
