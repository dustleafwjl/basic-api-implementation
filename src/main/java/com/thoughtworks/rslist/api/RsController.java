package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无标签"));
    rsEvents.add(new RsEvent("第二条事件", "无标签"));
    rsEvents.add(new RsEvent("第三条事件", "无标签"));
    return rsEvents;
  }

  @GetMapping("/rs/list/{index}")
  public RsEvent getRsEventByIndex(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventListLimit(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if(start == null && end == null) return rsList;
    return rsList.subList(start - 1, end);
  }

  @PostMapping("/rs/event")
  public void addRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
  }

  @PutMapping("/rs/event/{index}")
  public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
    rsList.get(index - 1).setEventName(rsEvent.getEventName());
    rsList.get(index - 1).setKeyWord(rsEvent.getKeyWord());
  }
}
