package com.lirugo.sus_jw.service;

import com.lirugo.sus_jw.dto.StandDayDto;
import com.lirugo.sus_jw.dto.request.StandDayAttendeeRequest;
import com.lirugo.sus_jw.entity.StandDayAttendee;
import com.lirugo.sus_jw.mapper.StandDayMapper;
import com.lirugo.sus_jw.repo.StandDayAttendeeRepo;
import com.lirugo.sus_jw.repo.StandDayRepo;
import com.lirugo.sus_jw.repo.TimeFrameRepo;
import com.lirugo.sus_jw.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StandDayService {
    private static final int STAND_RANGE_MNTHS = 1;
    private final UserRepo userRepo;
    private final StandDayAttendeeRepo standDayAttendeeRepo;
    private final StandDayRepo standDayRepo;
    private final TimeFrameRepo timeFrameRepo;
    private final StandDayMapper standDayMapper;

    public Optional<StandDayDto> getById(long id) {
        return standDayRepo.findById(id).map(standDayMapper::map);
    }

    public List<StandDayDto> getStandDays() {
        var currentDate = LocalDate.now();
        var startDate = currentDate.minusMonths(STAND_RANGE_MNTHS);
        var endDate = currentDate.plusMonths(STAND_RANGE_MNTHS);

        return standDayRepo.findAllWithinOneMonthRange(startDate, endDate).stream().map(standDayMapper::map).toList();
    }

    @Transactional
    public boolean assignUser(long id, StandDayAttendeeRequest request) {
        var standDay = standDayRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StandDay not found for id: " + id));
        var timeFrame = timeFrameRepo.findById(request.getTimeFrameId())
                .orElseThrow(() -> new EntityNotFoundException("TimeFrame not found for id: " + request.getTimeFrameId()));
        var attendee = userRepo.findById(request.getAttendeeId())
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + request.getAttendeeId()));

        var standDayAttendee = standDayAttendeeRepo.findByStandDayAndUserAndTimeFrame(standDay, attendee, timeFrame)
                .orElse(StandDayAttendee.builder()
                        .standDay(standDay)
                        .user(attendee)
                        .timeFrame(timeFrame)
                        .build());

        if (standDayAttendee.getId() != null) {
            log.warn("Attendee with id {} is already assigned to StandDay with id {}", request.getAttendeeId(), id);
            return false;
        }

        standDay.getAttendees().add(standDayAttendee);
        standDayRepo.save(standDay);
        return true;
    }

    @Transactional
    public void unassignUser(long id, StandDayAttendeeRequest request) {
        var standDay = standDayRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StandDay not found for id: " + id));

        var attendeeToRemove = standDay.getAttendees().stream()
                .filter(attendee ->
                        attendee.getUser().getId() == request.getAttendeeId() &&
                                attendee.getTimeFrame().getId() == request.getTimeFrameId())
                .findFirst();

        if (attendeeToRemove.isPresent()) {
            standDay.getAttendees().remove(attendeeToRemove.get());
            standDayRepo.save(standDay);
            log.info("Successfully unassigned attendee with id {} from StandDay with id {}", request.getAttendeeId(), id);
        } else {
            log.warn("Attendee {} not assigned to StandDay with id: {}", request.getAttendeeId(), id);
        }
    }
}
