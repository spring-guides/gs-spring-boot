package com.planespotter.infrastructure.gateways;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.adapters.FlightDataGateway;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MockFlightDataApiImpl implements FlightDataGateway {

    private static final List<Flight> mockFlights = new ArrayList<>();

    static {
        mockFlights.add(new Flight("1", "LH123", "Lufthansa", "FRA", "JFK", 
                LocalDateTime.of(2023, 10, 12, 14, 30), LocalDateTime.of(2023, 10, 12, 14, 45),
                LocalDateTime.of(2023, 10, 12, 18, 30), LocalDateTime.of(2023, 10, 12, 18, 25),
                "A388", "Departed", "A12", "25R", "B4", "13L"));
        mockFlights.add(new Flight("2", "UA456", "United", "FRA", "LAX", 
                LocalDateTime.of(2023, 10, 13, 16, 0), LocalDateTime.of(2023, 10, 13, 16, 15),
                LocalDateTime.of(2023, 10, 13, 19, 50), LocalDateTime.of(2023, 10, 13, 19, 45),
                "B744", "Departed", "B22", "18C", "G5", "24R"));
        // Add more mock flights as needed
    }

    @Override
    public List<Flight> getFlights(String airportIataCode) {
        return mockFlights.stream()
                .filter(flight -> flight.getOriginAirportIata().equalsIgnoreCase(airportIataCode) ||
                        flight.getDestinationAirportIata().equalsIgnoreCase(airportIataCode))
                .collect(Collectors.toList());
    }
}
