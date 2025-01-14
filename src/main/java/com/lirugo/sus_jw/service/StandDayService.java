package com.lirugo.sus_jw.service;

import com.lirugo.sus_jw.entity.StandDay;
import com.lirugo.sus_jw.repo.StandDayRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandDayService {
    private final StandDayRepo standDayRepo;

    public List<StandDay> getStandDays() {
        return standDayRepo.findAll();
    }
}
