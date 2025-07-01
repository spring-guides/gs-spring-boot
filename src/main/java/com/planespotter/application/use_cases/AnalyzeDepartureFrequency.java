package com.planespotter.application.use_cases;

import org.springframework.stereotype.Service;
import com.planespotter.domain.entities.Flight;

import java.util.List;
import java.util.Map;

@Service
public class AnalyzeDepartureFrequency {
    public Map<String, Long> execute(List<Flight> flights, String aircraftIcaoCode) {
        // Implement logic to group flights by departure hour and count occurrences
        return null;
    }
}