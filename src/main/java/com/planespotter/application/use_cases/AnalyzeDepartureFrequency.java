package com.planespotter.application.use_cases;

import com.planespotter.domain.entities.Flight;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AnalyzeDepartureFrequency {

    private static final DateTimeFormatter HOUR_FORMATTER = DateTimeFormatter.ofPattern("HH:00");

    public Map<String, Long> execute(List<Flight> flights, String aircraftIcaoCode) {
        return flights.stream()
                .filter(flight -> flight.getActualDepartureUtc() != null &&
                        (aircraftIcaoCode == null || aircraftIcaoCode.equals(flight.getAircraftIcao())))
                .collect(Collectors.groupingBy(
                        flight -> flight.getActualDepartureUtc().format(HOUR_FORMATTER),
                        Collectors.counting()
                ));
    }
}
