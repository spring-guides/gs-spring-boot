package com.planespotter.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    // Constructor
    public Flight(String id, String flightNumber, String airline, String originAirportIata, String destinationAirportIata, LocalDateTime scheduledDepartureUtc, LocalDateTime actualDepartureUtc, LocalDateTime scheduledArrivalUtc, LocalDateTime actualArrivalUtc, String aircraftIcao, String status, String departureGate, String departureRunway, String arrivalGate, String arrivalRunway) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.originAirportIata = originAirportIata;
        this.destinationAirportIata = destinationAirportIata;
        this.scheduledDepartureUtc = scheduledDepartureUtc;
        this.actualDepartureUtc = actualDepartureUtc;
        this.scheduledArrivalUtc = scheduledArrivalUtc;
        this.actualArrivalUtc = actualArrivalUtc;
        this.aircraftIcao = aircraftIcao;
        this.status = status;
        this.departureGate = departureGate;
        this.departureRunway = departureRunway;
        this.arrivalGate = arrivalGate;
        this.arrivalRunway = arrivalRunway;
    }

    // Getters, equals, hashCode, and toString methods
}