package com.planespotter.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AircraftType {
    private String icaoCode;
    private String manufacturer;
    private String model;
    private String description;

    // Constructor
    public AircraftType(String icaoCode, String manufacturer, String model, String description) {
        this.icaoCode = icaoCode;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
    }

    // Getters, equals, hashCode methods
}