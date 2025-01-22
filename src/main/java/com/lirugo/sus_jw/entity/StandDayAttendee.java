package com.lirugo.sus_jw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stand_day_attendees")
public class StandDayAttendee {

    @JsonIgnore
    @EmbeddedId
    private StandDayAttendeeId id;

    @JsonIgnore
    @MapsId("standDayId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stand_day_id", nullable = false)
    private StandDayEntity standDay;

    @MapsId("timeFrameId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_frame_id", nullable = false)
    private TimeFrameEntity timeFrame;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
