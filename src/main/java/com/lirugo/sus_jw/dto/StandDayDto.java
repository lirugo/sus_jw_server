package com.lirugo.sus_jw.dto;

import com.lirugo.sus_jw.entity.StandDayAttendee;
import com.lirugo.sus_jw.entity.TimeFrameEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandDayDto {
    private Long id;
    private StandDto stand;
    private LocalDate date;
    private List<TimeFrameEntity> timeFrames;
    private List<StandDayAttendee> attendees;
}
