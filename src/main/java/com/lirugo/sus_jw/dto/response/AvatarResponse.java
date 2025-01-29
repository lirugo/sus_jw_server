package com.lirugo.sus_jw.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AvatarResponse {
    private final byte[] imageBytes;
    private final String contentType;
}
