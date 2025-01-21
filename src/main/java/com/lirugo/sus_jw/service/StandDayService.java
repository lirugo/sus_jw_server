package com.lirugo.sus_jw.service;

import com.lirugo.sus_jw.dto.StandDayDto;
import com.lirugo.sus_jw.mapper.StandDayMapper;
import com.lirugo.sus_jw.repo.StandDayRepo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandDayService {
    private final StandDayRepo standDayRepo;
    private final StandDayMapper standDayMapper;

    public Optional<StandDayDto> getById(long id) {
        return standDayRepo.findById(id).map(standDayMapper::map);
    }

    public List<StandDayDto> getStandDays() {
        return standDayRepo.findAll().stream().map(standDayMapper::map).toList();
    }
}
