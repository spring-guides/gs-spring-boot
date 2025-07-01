package com.planespotter.application.use_cases;

import org.springframework.stereotype.Service;
import com.planespotter.infrastructure.adapters.FlightRepository;
import com.planespotter.domain.entities.Flight;

import java.util.List;

@Service
public class GetFlightsByAirport {
    private final FlightRepository flightRepository;

    public GetFlightsByAirport(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> execute(String airportIataCode, String type) {
        // Implement logic to filter flights based on airportIataCode and type
        return flightRepository.findAll();
    }
}