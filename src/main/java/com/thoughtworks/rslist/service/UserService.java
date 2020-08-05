package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;

import java.util.List;

public class UserService {
    static public boolean addUser(User user) {
        UserList.userList.add(user);
        return true;
    }
    static public List<User> getUsers() {
        return UserList.userList;
    }
}
