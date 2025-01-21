package com.lirugo.sus_jw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lirugo.sus_jw.dto.UserDto;
import com.lirugo.sus_jw.entity.UserEntity;
import com.lirugo.sus_jw.mapper.UserMapper;
import com.lirugo.sus_jw.repo.UserRepo;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final UserRepo userRepo;

    @Value("${app.auth.username-white-list}")
    private List<String> usernameWhiteList;

    public boolean isInWhiteList(String username) {
        var isInWhiteList = usernameWhiteList.contains("ANY") || usernameWhiteList.contains(username);
        log.info("User {} is in white list: {}", username, isInWhiteList);

        return isInWhiteList;
    }

    public UserDto save(UserDto userDto) {
        var userEntity = userRepo.findByUsername(userDto.getUsername()).orElseGet(() -> UserEntity.builder()
                .telegramId(userDto.getTelegramId())
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .photoUrl(userDto.getPhotoUrl())
                .build()
        );

        userRepo.save(userEntity);

        return userMapper.map(userEntity);
    }

    public UserDto decodeTgUser(String userJson) throws JsonProcessingException {
        var decodedData = URLDecoder.decode(userJson, StandardCharsets.UTF_8);
        var userDto = objectMapper.readValue(decodedData, UserDto.class);

        // Replace the telegramId with the id
        userDto.setTelegramId(userDto.getId());
        userDto.setId(null);

        return userDto;
    }
}
