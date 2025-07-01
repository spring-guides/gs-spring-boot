package com.planespotter.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    private String id;
    private String flightNumber;
    private String airline;
    private String originAirportIata;
    private String destinationAirportIata;
    private LocalDateTime scheduledDepartureUtc;
    private LocalDateTime actualDepartureUtc;
    private LocalDateTime scheduledArrivalUtc;
    private LocalDateTime actualArrivalUtc;
    private String aircraftIcao;
    private String status;
    private String departureGate;
    private String departureRunway;
    private String arrivalGate;
    private String arrivalRunway;
}
