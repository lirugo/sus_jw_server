package com.lirugo.sus_jw.repo;

import com.lirugo.sus_jw.entity.StandDayAttendee;
import com.lirugo.sus_jw.entity.StandDayEntity;
import com.lirugo.sus_jw.entity.TimeFrameEntity;
import com.lirugo.sus_jw.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandDayAttendeeRepo extends JpaRepository<StandDayAttendee, Long> {
    Optional<StandDayAttendee> findByStandDayAndUserAndTimeFrame(StandDayEntity standDay, UserEntity user, TimeFrameEntity timeFrame);
}
