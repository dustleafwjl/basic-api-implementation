package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;

import java.util.List;

public class UserService {
    static public int addUser(User user) {
        UserList.userList.add(user);
        return UserList.userList.size();
    }
    static public List<User> getUsers() {
        return UserList.userList;
    }
}
