package com.planespotter.application.use_cases;

import org.springframework.stereotype.Service;
import com.planespotter.domain.entities.Flight;

import java.util.List;

@Service
public class FilterFlightsByAircraftType {
    public List<Flight> execute(List<Flight> flights, String aircraftIcaoCode) {
        // Implement logic to filter flights by aircraftIcaoCode
        return flights;
    }
}