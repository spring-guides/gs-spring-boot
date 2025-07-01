package com.planespotter.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    private String iataCode;
    private String name;
    private String city;
    private String country;

    // Constructor
    public Airport(String iataCode, String name, String city, String country) {
        this.iataCode = iataCode;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    // Getters, equals, hashCode methods
}