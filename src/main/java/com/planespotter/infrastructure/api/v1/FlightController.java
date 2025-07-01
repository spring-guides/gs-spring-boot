package com.planespotter.infrastructure.api.v1;

import com.planespotter.application.use_cases.GetFlightsByAirport;
import com.planespotter.application.use_cases.FilterFlightsByAircraftType;
import com.planespotter.application.use_cases.AnalyzeDepartureFrequency;
import com.planespotter.infrastructure.presenters.FlightPresenter;
import com.planespotter.infrastructure.presenters.FrequencyGraphPresenter;
import com.planespotter.infrastructure.presenters.dto.FlightDto;
import com.planespotter.infrastructure.presenters.dto.FrequencyDataPointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private GetFlightsByAirport getFlightsByAirport;

    @Autowired
    private FilterFlightsByAircraftType filterFlightsByAircraftType;

    @Autowired
    private AnalyzeDepartureFrequency analyzeDepartureFrequency;

    @Autowired
    private FlightPresenter flightPresenter;

    @Autowired
    private FrequencyGraphPresenter frequencyGraphPresenter;

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(@RequestParam(defaultValue = "FRA") String airport_code,
                                                      @RequestParam(required = false) String aircraft_type,
                                                      @RequestParam(required = false) String type) {
        try {
            List<Flight> flights = getFlightsByAirport.execute(airport_code, type);
            if (aircraft_type != null) {
                flights = filterFlightsByAircraftType.execute(flights, aircraft_type);
            }
            List<FlightDto> flightDtos = flightPresenter.toDtoList(flights);
            return ResponseEntity.ok(flightDtos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/frequency")
    public ResponseEntity<List<FrequencyDataPointDto>> getDepartureFrequency(@RequestParam(defaultValue = "FRA") String airport_code,
                                                                             @RequestParam(required = false) String aircraft_type) {
        try {
            List<Flight> departures = getFlightsByAirport.execute(airport_code, "departures");
            List<FrequencyDataPointDto> frequencyData = frequencyGraphPresenter.toDtoList(
                    analyzeDepartureFrequency.execute(departures, aircraft_type)
            );
            return ResponseEntity.ok(frequencyData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
