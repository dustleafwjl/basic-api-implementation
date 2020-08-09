package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public int addUser(User user) {
        UserDto userDto = UserDto.builder().userName(user.getUserName()).age(user.getAge())
                .gender(user.getGender()).phone(user.getPhone()).email(user.getEmail()).voteNum(10).build();
        userRepository.save(userDto);
        return (int)userRepository.count();
    }

    public void removeUserById(int userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(int userId) {
        UserDto userDto = userRepository.findById(userId).orElse(new UserDto());
        User user = User.builder().userName(userDto.getUserName()).age(userDto.getAge())
                    .gender(userDto.getGender()).email(userDto.getEmail()).phone(userDto.getPhone()).build();
        return user;
    }
    public List<User> getUsers() {
        List<User> users = userRepository.findAll().stream()
                .map(ele -> User.builder()
                        .age(ele.getAge()).userName(ele.getUserName())
                        .email(ele.getEmail()).gender(ele.getGender())
                        .phone(ele.getPhone()).voteNum(ele.getVoteNum())
                        .build()).collect(Collectors.toList());
        return users;
    }
}
