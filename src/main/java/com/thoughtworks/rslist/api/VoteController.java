package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/rs/vote/{eventId}")
    public ResponseEntity vote(@PathVariable int eventId, @RequestBody Vote vote) {
        voteService.addVoteRecord(eventId, vote);
        return ResponseEntity.created(null).build();
    }


    @GetMapping(value = "/rs/vote", params = "startTime")
    public ResponseEntity getVoteRecordByTime(@RequestParam String startTime, @RequestParam String endTime) {
        List<Vote> votes = voteService.getVoteRecordByTime(startTime, endTime);
        return ResponseEntity.ok(votes);
    }
    @GetMapping("/rs/vote")
    public ResponseEntity getVoteRecord(@RequestParam(required = false) Integer eventId,
                                        @RequestParam(required = false) Integer userId,
                                        @RequestParam(required = false) Integer pageIndex) {
        List<Vote> votes = voteService.getVoteRecord(eventId, userId, pageIndex);
        return ResponseEntity.ok(votes);
    }


}
