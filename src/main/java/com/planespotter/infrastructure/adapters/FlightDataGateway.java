package com.planespotter.infrastructure.adapters;

import com.planespotter.domain.entities.Flight;
import java.util.List;

public interface FlightDataGateway {
    List<Flight> getFlights(String airportIataCode);
}
