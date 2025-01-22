package com.lirugo.sus_jw.mapper;

import com.lirugo.sus_jw.dto.UserDto;
import com.lirugo.sus_jw.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "telegramId", target = "telegramId")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "photoUrl", target = "photoUrl")
    UserDto map(UserEntity source);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "telegramId", target = "telegramId")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "photoUrl", target = "photoUrl")
    UserEntity toEntity(UserDto source);
}
