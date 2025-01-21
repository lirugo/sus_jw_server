package com.lirugo.sus_jw.mapper;

import com.lirugo.sus_jw.dto.StandDayDto;
import com.lirugo.sus_jw.entity.StandDayEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandDayMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "stand", target = "stand")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "timeFrames", target = "timeFrames")
    @Mapping(source = "attendees", target = "attendees")
    StandDayDto map(StandDayEntity entity);
}
