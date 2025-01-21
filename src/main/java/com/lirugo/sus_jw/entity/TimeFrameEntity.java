package com.lirugo.sus_jw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.Data;


@Data
@Entity
@Table(name = "time_frames")
public class TimeFrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_time")
    private LocalTime fromTime;

    @Column(name = "toTime")
    private LocalTime toTime;
}
