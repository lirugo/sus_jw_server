package com.lirugo.sus_jw.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InfoController {

    @Value("${app.version}")
    private String version;

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        return Map.of(
                "version", version
        );
    }
}
