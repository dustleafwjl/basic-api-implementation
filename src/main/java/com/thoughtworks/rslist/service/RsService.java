package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.RsEventNotValidException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public class RsService {
    private final RsEventRepository rsEventRepository;
    private final UserRepository userRepository;

    public RsService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
    }

    public RsEvent getRsEvent(int index) {

        RsEventDto rsEventDto = rsEventRepository.findById(index).orElse(new RsEventDto());
        return new RsEvent(rsEventDto.getEventName(), rsEventDto.getKeyWord(), rsEventDto.getUserDto().getId());
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
        RsEventDto rsEventDto = rsEventRepository.save(RsEventDto.builder()
                .eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWord()).userDto(userDto).build());
        return rsEventDto.getId();
    }
    public void removeRsEvent(int index) {
        try {
            rsEventRepository.deleteById(index);
        } catch (Exception e) {

        }
    }
    public void updateRsEvent(int index, RsEvent rsEvent) {
        RsEventDto rsEventDto = rsEventRepository.findById(index).orElse(null);
        if(rsEventDto != null) {
            rsEventDto.setEventName(rsEvent.getEventName());
            rsEventDto.setKeyWord(rsEvent.getKeyWord());
            rsEventRepository.save(rsEventDto);
        }
    }
}
