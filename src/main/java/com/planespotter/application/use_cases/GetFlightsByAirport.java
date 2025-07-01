package com.planespotter.application.use_cases;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.adapters.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFlightsByAirport {

    private final FlightRepository flightRepository;

    @Autowired
    public GetFlightsByAirport(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> execute(String airportIataCode, String type) {
        return flightRepository.findAll().stream()
                .filter(flight -> "departures".equals(type) && airportIataCode.equals(flight.getOriginAirportIata()) ||
                        "arrivals".equals(type) && airportIataCode.equals(flight.getDestinationAirportIata()))
                .collect(Collectors.toList());
    }
}
