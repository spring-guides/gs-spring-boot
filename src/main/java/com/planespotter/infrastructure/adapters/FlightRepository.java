package com.planespotter.infrastructure.adapters;

import com.planespotter.domain.entities.Flight;

import java.util.List;

public interface FlightRepository {
    List<Flight> findAll();
    List<Flight> findByOriginAirportIata(String iataCode);
    List<Flight> findByDestinationAirportIata(String iataCode);
    void saveAll(List<Flight> flights);
}
