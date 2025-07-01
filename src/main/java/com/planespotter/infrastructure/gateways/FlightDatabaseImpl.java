package com.planespotter.infrastructure.gateways;

import com.planespotter.domain.entities.Flight;
import com.planespotter.infrastructure.adapters.FlightRepository;
import com.planespotter.infrastructure.persistence.entities.FlightJpaEntity;
import com.planespotter.infrastructure.persistence.repositories.FlightJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightDatabaseImpl implements FlightRepository {

    @Autowired
    private FlightJpaRepository flightJpaRepository;

    @Autowired
    private MockFlightDataApiImpl mockFlightDataApi;

    @PostConstruct
    public void initDatabase() {
        List<Flight> flights = mockFlightDataApi.getFlights("FRA");
        List<FlightJpaEntity> jpaEntities = flights.stream().map(this::toJpaEntity).collect(Collectors.toList());
        flightJpaRepository.saveAll(jpaEntities);
    }

    @Override
    public List<Flight> findAll() {
        return flightJpaRepository.findAll().stream().map(this::toDomainEntity).collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByOriginAirportIata(String iataCode) {
        return flightJpaRepository.findAll().stream()
                .filter(flight -> flight.getOriginAirportIata().equals(iataCode))
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDestinationAirportIata(String iataCode) {
        return flightJpaRepository.findAll().stream()
                .filter(flight -> flight.getDestinationAirportIata().equals(iataCode))
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<Flight> flights) {
        List<FlightJpaEntity> jpaEntities = flights.stream().map(this::toJpaEntity).collect(Collectors.toList());
        flightJpaRepository.saveAll(jpaEntities);
    }

    private FlightJpaEntity toJpaEntity(Flight flight) {
        return new FlightJpaEntity(
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

    private Flight toDomainEntity(FlightJpaEntity jpaEntity) {
        return new Flight(
                jpaEntity.getId(),
                jpaEntity.getFlightNumber(),
                jpaEntity.getAirline(),
                jpaEntity.getOriginAirportIata(),
                jpaEntity.getDestinationAirportIata(),
                jpaEntity.getScheduledDepartureUtc(),
                jpaEntity.getActualDepartureUtc(),
                jpaEntity.getScheduledArrivalUtc(),
                jpaEntity.getActualArrivalUtc(),
                jpaEntity.getAircraftIcao(),
                jpaEntity.getStatus(),
                jpaEntity.getDepartureGate(),
                jpaEntity.getDepartureRunway(),
                jpaEntity.getArrivalGate(),
                jpaEntity.getArrivalRunway()
        );
    }
}
