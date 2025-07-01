package com.planespotter.application.use_cases;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.adapters.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetFlightsByAirport {

    @Autowired
    private FlightRepository flightRepository;

    public List<Flight> execute(String airportIataCode, String type) {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .filter(flight -> type.equalsIgnoreCase("departures") ? 
                        flight.getOriginAirportIata().equalsIgnoreCase(airportIataCode) : 
                        flight.getDestinationAirportIata().equalsIgnoreCase(airportIataCode))
                .collect(Collectors.toList());
    }
}
