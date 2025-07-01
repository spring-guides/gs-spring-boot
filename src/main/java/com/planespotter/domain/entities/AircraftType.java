package com.planespotter.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftType {
    private String icaoCode;
    private String manufacturer;
    private String model;
    private String description;
}
