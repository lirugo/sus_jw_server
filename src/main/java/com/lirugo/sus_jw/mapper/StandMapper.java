package com.lirugo.sus_jw.mapper;

import com.lirugo.sus_jw.dto.StandDto;
import com.lirugo.sus_jw.entity.StandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "latitude", target = "latitude")
    @Mapping(source = "longitude", target = "longitude")
    StandDto map(StandEntity source);
}
