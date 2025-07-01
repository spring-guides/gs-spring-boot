package com.planespotter.infrastructure.gateways;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.adapters.FlightDataGateway;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MockFlightDataApiImpl implements FlightDataGateway {

    private static final List<Flight> MOCK_FLIGHTS = Arrays.asList(
            new Flight("1", "LH123", "Lufthansa", "FRA", "JFK",
                    LocalDateTime.of(2023, 10, 10, 14, 0), LocalDateTime.of(2023, 10, 10, 14, 30),
                    LocalDateTime.of(2023, 10, 10, 18, 0), LocalDateTime.of(2023, 10, 10, 18, 30),
                    "A388", "departed", "A12", "25R", "B34", "22L"),
            new Flight("2", "LH124", "Lufthansa", "FRA", "LAX",
                    LocalDateTime.of(2023, 10, 10, 16, 0), LocalDateTime.of(2023, 10, 10, 16, 15),
                    LocalDateTime.of(2023, 10, 10, 19, 0), LocalDateTime.of(2023, 10, 10, 19, 15),
                    "B777", "departed", "A14", "26L", "C12", "30R")
            // Add more mock data as needed
    );

    @Override
    public List<Flight> getFlights(String airportIataCode) {
        return MOCK_FLIGHTS.stream()
                .filter(flight -> airportIataCode.equals(flight.getOriginAirportIata()) ||
                        airportIataCode.equals(flight.getDestinationAirportIata()))
                .collect(Collectors.toList());
    }
}
