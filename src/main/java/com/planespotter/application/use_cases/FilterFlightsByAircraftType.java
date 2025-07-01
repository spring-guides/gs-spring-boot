package com.planespotter.application.use_cases;

import com.planespotter.domain.entities.Flight;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterFlightsByAircraftType {

    public List<Flight> execute(List<Flight> flights, String aircraftIcaoCode) {
        return flights.stream()
                .filter(flight -> flight.getAircraftIcao().equalsIgnoreCase(aircraftIcaoCode))
                .collect(Collectors.toList());
    }
}
