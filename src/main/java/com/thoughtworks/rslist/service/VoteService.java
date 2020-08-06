package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.VoteDto;
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

    public int addVoteRecord(int eventId, Vote vote) {
        int voteNum = vote.getVoteNum();
        int userVoteNum = userRepository.findById(vote.getUserId()).orElse(null).getVoteNum();
        if(voteNum > userVoteNum) {
            return -1;
        }
        VoteDto voteDto = VoteDto.builder().eventId(eventId).userId(vote.getUserId()).voteNum(vote.getVoteNum())
                .voteTime(vote.getVoteTime()).build();
        voteRepository.save(voteDto);
        return 1;
    }
}
