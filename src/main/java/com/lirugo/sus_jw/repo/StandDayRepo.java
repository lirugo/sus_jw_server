package com.lirugo.sus_jw.repo;

import com.lirugo.sus_jw.entity.StandDayEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StandDayRepo extends JpaRepository<StandDayEntity, Long> {
    @Query("SELECT s FROM StandDayEntity s WHERE s.date BETWEEN :startDate AND :endDate")
    List<StandDayEntity> findAllWithinOneMonthRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<StandDayEntity> findByDate(LocalDate date);
}
