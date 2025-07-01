package com.planespotter.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String type;
    private String identifier;

    // Constructor
    public Location(String type, String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    // Getters, equals, hashCode methods
}