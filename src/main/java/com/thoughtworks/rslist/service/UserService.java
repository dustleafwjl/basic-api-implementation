package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public int addUser(User user) {
        UserDto userDto = UserDto.builder().userName(user.getUserName()).age(user.getAge())
                .gender(user.getGender()).phone(user.getPhone()).voteNum(10).build();
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
        return UserList.userList;
    }
}
