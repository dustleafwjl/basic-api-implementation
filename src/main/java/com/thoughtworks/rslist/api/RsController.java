package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private final List<RsEvent> rsList = initRsEventList();
  private final List<User> userList = UserList.userList;
  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));
    rsEvents.add(new RsEvent("第二条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));
    rsEvents.add(new RsEvent("第三条事件", "无标签", new User("wjl", "male", 18, "wangjianlin@demo.com", "11122223333")));

    return rsEvents;
  }

  @GetMapping("/rs/list/{index}")
  public ResponseEntity getRsEventByIndex(@PathVariable int index) {
    return ResponseEntity.ok(rsList.get(index - 1));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventListLimit(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if(start == null && end == null) {
      return ResponseEntity.ok(rsList);
    }
    return ResponseEntity.ok(rsList.subList(start - 1, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    User user = rsEvent.getUser();
    boolean userIsNotExist = userList.stream().noneMatch(ele -> ele.equals(user));
    if(userIsNotExist) {
      userList.add(user);
    }
    rsList.add(rsEvent);
    return ResponseEntity.created(null).header("index", String.valueOf(userList.size())).build();
  }

  @PutMapping("/rs/event/{index}")
  public ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
    rsList.get(index - 1).setEventName(rsEvent.getEventName());
    rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
    return ResponseEntity.ok().build();
  }
  @DeleteMapping("/rs/event/{index}")
  public ResponseEntity removeRsEvent(@PathVariable int index) {
    rsList.remove(index - 1);
    return ResponseEntity.ok().build();
  }
}
