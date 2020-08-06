package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private final List<User> userList = UserList.userList;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/user")
    public ResponseEntity registerUser(@Valid @RequestBody User user) {
        int index = userService.addUser(user);
        return ResponseEntity.created(null).header("index", String.valueOf(index)).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
