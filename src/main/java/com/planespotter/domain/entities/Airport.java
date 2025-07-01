package com.planespotter.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    private String iataCode;
    private String name;
    private String city;
    private String country;
}
