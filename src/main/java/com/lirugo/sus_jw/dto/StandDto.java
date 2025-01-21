package com.lirugo.sus_jw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandDto {
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
}
