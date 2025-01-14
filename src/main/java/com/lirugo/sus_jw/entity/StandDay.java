package com.lirugo.sus_jw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "stand_days")
public class StandDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "stand_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Stand stand;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany
    @JoinTable(
            name = "stand_day_time_frames",
            joinColumns = @JoinColumn(name = "stand_day_id"),
            inverseJoinColumns = @JoinColumn(name = "time_frame_id")
    )
    private List<TimeFrame> timeFrames;

    @OneToMany
    @JoinTable(
            name = "stand_day_attendees",
            joinColumns = @JoinColumn(name = "stand_day_time_frame_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees;
}
