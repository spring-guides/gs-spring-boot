package com.planespotter.application.use_cases;

import com.planespotter.domain.entities.Flight;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyzeDepartureFrequency {

    public Map<String, Long> execute(List<Flight> flights, String aircraftIcaoCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:00");
        return flights.stream()
                .filter(flight -> flight.getActualDepartureUtc() != null && 
                        (aircraftIcaoCode == null || flight.getAircraftIcao().equalsIgnoreCase(aircraftIcaoCode)))
                .collect(Collectors.groupingBy(
                        flight -> flight.getActualDepartureUtc().format(formatter),
                        Collectors.counting()
                ));
    }
}
