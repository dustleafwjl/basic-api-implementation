package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    @Autowired
    VoteService voteService;

    @PostMapping("/rs/vote/{eventId}")
    public ResponseEntity vote(@PathVariable int eventId, @RequestBody Vote vote) {
        if(voteService.addVoteRecord(eventId, vote) == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(null).build();
    }
}
