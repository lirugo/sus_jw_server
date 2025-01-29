package com.lirugo.sus_jw.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lirugo.sus_jw.dto.UserDto;
import com.lirugo.sus_jw.dto.response.AvatarResponse;
import com.lirugo.sus_jw.entity.UserEntity;
import com.lirugo.sus_jw.mapper.UserMapper;
import com.lirugo.sus_jw.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public UserDto getById(Long id) {
        return userRepo.findById(id).map(userMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + id));
    }

    public UserDto getByTelegramId(long telegramId) {
        return userRepo.findByTelegramId(telegramId).map(userMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("User not found for telegramId: " + telegramId));
    }

    public UserDto getByUsername(String username) {
        return userRepo.findByUsername(username).map(userMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("User not found for username: " + username));
    }

    public UserDto save(UserDto userDto) {
        var userEntity = userRepo.findByTelegramId(userDto.getTelegramId()).orElseGet(() -> UserEntity.builder()
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

    public Optional<AvatarResponse> getAvatar(Long id) {
        var user = getById(id);
        var restTemplate = new RestTemplate();

        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity("https://cdn.pixabay.com/photo/2021/07/02/04/48/user-6380868_1280.png", byte[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String contentType = Optional.ofNullable(response.getHeaders().getContentType())
                        .map(MediaType::toString)
                        .orElse("application/octet-stream");

                return Optional.of(new AvatarResponse(response.getBody(), contentType));
            }
        } catch (Exception e) {
            log.error("Failed to fetch avatar: " + e.getMessage());
        }

        return Optional.empty();
    }
}
