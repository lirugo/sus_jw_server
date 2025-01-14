package com.lirugo.sus_jw.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${app.version}")
    private String version;

    @GetMapping
    public Map<String, String> getInfo() {
        return Map.of(
                "version", version
        );
    }
}
