package com.lirugo.sus_jw.repo;

import com.lirugo.sus_jw.entity.UserEntity;
import com.lirugo.sus_jw.mapper.UserMapper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByTelegramId(long telegramId);
}