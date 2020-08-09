package com.thoughtworks.rslist;


import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsService;
import com.thoughtworks.rslist.service.UserService;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RsService rsService(RsEventRepository rsEventRepository, UserRepository userRepository) {
        return new RsService(rsEventRepository, userRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public VoteService voteService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        return new VoteService(rsEventRepository, userRepository, voteRepository);
    }

//    @Bean
//    public RsController rsController(RsService rsService) {
//        return new RsController(rsService);
//    }
}
