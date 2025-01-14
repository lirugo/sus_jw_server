package com.lirugo.sus_jw.controller;

import com.lirugo.sus_jw.entity.StandDay;
import com.lirugo.sus_jw.service.StandDayService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stand-days")
public class StandDayController {

    private final StandDayService standDayService;

    @GetMapping
    public List<StandDay> getStands() {
        return standDayService.getStandDays();
    }
}
