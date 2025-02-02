package com.lirugo.sus_jw.controller;

import com.lirugo.sus_jw.dto.StandDayDto;
import com.lirugo.sus_jw.dto.request.StandDayAttendeeRequest;
import com.lirugo.sus_jw.service.StandDayService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stand-days")
public class StandDayController {

    private final StandDayService standDayService;

    @GetMapping
    public List<StandDayDto> getStands() {
        return standDayService.getStandDays();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandDayDto> getStand(@PathVariable long id) {
        return standDayService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/assign")
    public ResponseEntity<Void> assignUser(@PathVariable long id, @RequestBody StandDayAttendeeRequest request) {
        return standDayService.assignUser(id, request)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/{id}/unassign")
    public ResponseEntity<Void> unassignUser(@PathVariable long id, @RequestBody StandDayAttendeeRequest request) {
        standDayService.unassignUser(id, request);
        return ResponseEntity.ok().build();
    }
}
