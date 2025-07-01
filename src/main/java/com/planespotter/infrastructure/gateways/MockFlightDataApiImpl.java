package com.planespotter.infrastructure.gateways;

import org.springframework.stereotype.Component;
import com.planespotter.infrastructure.adapters.FlightDataGateway;
import com.planespotter.domain.entities.Flight;

import java.util.List;

@Component
public class MockFlightDataApiImpl implements FlightDataGateway {
    @Override
    public List<Flight> getFlights(String airportIataCode) {
        // Implement logic to return a filtered subset of mock data based on airportIataCode
        return null;
    }
}