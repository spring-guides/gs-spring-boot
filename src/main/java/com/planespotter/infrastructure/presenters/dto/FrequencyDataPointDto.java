package com.planespotter.infrastructure.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrequencyDataPointDto {
    private String hour;
    private Long count;
}
