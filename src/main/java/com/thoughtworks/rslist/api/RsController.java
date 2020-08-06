package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class RsController {
  @GetMapping("/rs/list/{index}")
  public ResponseEntity getRsEventByIndex(@PathVariable int index) {
    return ResponseEntity.ok(RsService.getInstance().getRsEvent(index));
  }

  @GetMapping("/rs/list")
  public ResponseEntity getRsEventListLimit(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
    if(start == null && end == null) {
      return ResponseEntity.ok(RsService.getInstance().getAllRsList());
    }
    return ResponseEntity.ok(RsService.getInstance().getRsListForLimit(start, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
    int index = RsService.getInstance().addRsEvent(rsEvent);
    return ResponseEntity.created(null).header("index", String.valueOf(index)).build();
  }

  @PutMapping("/rs/event/{index}")
  public ResponseEntity updateRsEvent(@PathVariable int index, @RequestBody @Valid RsEvent rsEvent) {
    RsService.getInstance().updateRsEvent(index, rsEvent);
    return ResponseEntity.ok().build();
  }
  @DeleteMapping("/rs/event/{index}")
  public ResponseEntity removeRsEvent(@PathVariable int index) {
    RsService.getInstance().removeRsEvent(index);
    return ResponseEntity.ok().build();
  }
}
