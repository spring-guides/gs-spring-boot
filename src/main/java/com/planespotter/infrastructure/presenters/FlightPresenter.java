package com.planespotter.infrastructure.presenters;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.presenters.dto.FlightDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightPresenter {

    public FlightDto toDto(Flight flight) {
        return new FlightDto(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getOriginAirportIata(),
                flight.getDestinationAirportIata(),
                flight.getScheduledDepartureUtc(),
                flight.getActualDepartureUtc(),
                flight.getScheduledArrivalUtc(),
                flight.getActualArrivalUtc(),
                flight.getAircraftIcao(),
                flight.getStatus(),
                flight.getDepartureGate(),
                flight.getDepartureRunway(),
                flight.getArrivalGate(),
                flight.getArrivalRunway()
        );
    }

    public List<FlightDto> toDtoList(List<Flight> flights) {
        return flights.stream().map(this::toDto).collect(Collectors.toList());
    }
}
