package com.thoughtworks.rslist.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserList {
    static public List<User> userList = new ArrayList<User>(Arrays.asList(
            new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333"),
            new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333"),
            new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));
}
