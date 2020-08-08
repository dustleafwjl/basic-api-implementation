package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class RsController {

  @Autowired
  RsService rsService;

  @GetMapping("/rs/list/{index}")
  public ResponseEntity getRsEventByIndex(@PathVariable int index) {
    RsEvent rsEvent = rsService.getRsEvent(index);
    if(rsEvent == null) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(rsEvent);
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventListLimit(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if(start == null && end == null) {
      return ResponseEntity.ok(rsService.getAllRsList());
    }
    return ResponseEntity.ok(rsService.getRsListForLimit(start, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    int index = rsService.addRsEvent(rsEvent);
    if(index == -1) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.created(null).header("index", String.valueOf(index)).build();
  }

  @DeleteMapping("/rs/event/{index}")
  public ResponseEntity removeRsEvent(@PathVariable int index) {
    rsService.removeRsEvent(index);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/rs/event/{index}")
  public ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
    rsService.updateRsEvent(index, rsEvent);
    return ResponseEntity.ok().build();
  }
}
