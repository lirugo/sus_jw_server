package com.lirugo.sus_jw.controller;


import com.lirugo.sus_jw.dto.UserDto;
import com.lirugo.sus_jw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @GetMapping("/auth")
    public UserDto getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found.");
        }

        var principal = authentication.getPrincipal();
        return userService.getByTelegramId(Long.parseLong(principal.toString()));
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<ByteArrayResource> getUserAvatar(@PathVariable Long id) {
        return userService.getAvatar(id)
                .map(avatarResponse -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(avatarResponse.getContentType()));
                    headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");

                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(new ByteArrayResource(avatarResponse.getImageBytes()));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
