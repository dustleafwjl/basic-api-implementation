package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    public int addVoteRecord(int eventId, Vote vote) {
        int voteNum = vote.getVoteNum();
        UserDto userDto = userRepository.findById(vote.getUserId()).orElse(null);
        RsEventDto rsEventDto = rsEventRepository.findById(vote.getEventId()).orElse(null);
        if(voteNum > userDto.getVoteNum()) {
            return -1;
        }
        VoteDto voteDto = VoteDto.builder().eventId(eventId).userId(vote.getUserId()).voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime()).build();
//        .rsEventDto(rsEventDto).userDto(userDto)
        voteRepository.save(voteDto);
        return 1;
    }

}
