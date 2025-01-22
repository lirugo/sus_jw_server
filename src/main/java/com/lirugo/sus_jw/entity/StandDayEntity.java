package com.lirugo.sus_jw.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(name = "stand_days")
public class StandDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "stand_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private StandEntity stand;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany
    @JoinTable(
            name = "stand_day_time_frames",
            joinColumns = @JoinColumn(name = "stand_day_id"),
            inverseJoinColumns = @JoinColumn(name = "time_frame_id")
    )
    private List<TimeFrameEntity> timeFrames;

    @JsonBackReference
    @OneToMany(mappedBy = "standDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StandDayAttendee> attendees;
}
