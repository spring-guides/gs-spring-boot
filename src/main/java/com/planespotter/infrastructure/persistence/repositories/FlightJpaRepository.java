package com.planespotter.infrastructure.persistence.repositories;

import com.planespotter.infrastructure.persistence.entities.FlightJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightJpaRepository extends JpaRepository<FlightJpaEntity, String> {
}
