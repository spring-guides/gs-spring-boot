package com.planespotter.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightJpaEntity {

    @Id
    private String id;

    private String flightNumber;
    private String airline;
    private String originAirportIata;
    private String destinationAirportIata;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime scheduledDepartureUtc;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime actualDepartureUtc;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime scheduledArrivalUtc;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime actualArrivalUtc;

    private String aircraftIcao;
    private String status;
    private String departureGate;
    private String departureRunway;
    private String arrivalGate;
    private String arrivalRunway;
}
