package com.lirugo.sus_jw.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record StandDayAttendeeId(
        Long standDayId,
        Long timeFrameId,
        Long userId
) implements Serializable {}
