package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public VoteService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public void addVoteRecord(int eventId, Vote vote) {
        int voteNum = vote.getVoteNum();
        UserDto userDto = userRepository.findById(vote.getUserId()).orElse(null);
        RsEventDto rsEventDto = rsEventRepository.findById(eventId).orElse(null);
        if(userDto == null || rsEventDto == null || voteNum > userDto.getVoteNum()) {
            throw new RuntimeException("params wrong");
        }
        userDto.setVoteNum(userDto.getVoteNum() - voteNum);
        rsEventDto.setVoteNum(voteNum + rsEventDto.getVoteNum());
        VoteDto voteDto = VoteDto.builder().voteNum(vote.getVoteNum())
                .rsEventDto(rsEventDto).userDto(userDto)
                .voteTime(vote.getVoteTime()).build();
        userRepository.save(userDto);
        voteRepository.save(voteDto);
        rsEventRepository.save(rsEventDto);
    }

    public List<Vote> getVoteRecord(Integer eventId, Integer userId, Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex-1, 5);
        List<VoteDto> voteDtos;
        if(eventId != null && userId != null) {
            voteDtos = voteRepository.findAccordingToEventIdAndUserId(eventId, userId, pageable);
        }else if(eventId != null) {
            voteDtos = voteRepository.findAccordingToEventId(eventId, pageable);
        } else if(userId != null) {
            voteDtos = voteRepository.findAccordingToUserId(userId, pageable);
        } else {
            throw new RuntimeException("wrong params");
        }
        List<Vote> votes = voteDtos.stream()
                .map(ele -> Vote.builder().userId(ele.getUserDto().getId()).eventId(ele.getRsEventDto().getId())
                        .voteNum(ele.getVoteNum()).voteTime(ele.getVoteTime())
                        .build()).collect(Collectors.toList());
        return votes;
    }


    public List<Vote> getVoteRecordByTime(String startTime, String endTime) {
        List<VoteDto> voteDtos = voteRepository.findAccordingToTimeLimit(startTime, endTime);
        List<Vote> votes = voteDtos.stream()
                .map(ele -> Vote.builder().userId(ele.getUserDto().getId()).eventId(ele.getRsEventDto().getId())
                        .voteNum(ele.getVoteNum()).voteTime(ele.getVoteTime())
                        .build()).collect(Collectors.toList());
        return votes;
    }
}
