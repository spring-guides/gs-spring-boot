### Phase 2: Entities and Use Cases Implementation

In this phase, the core domain entities and application-specific use cases have been implemented for the Plane Spotter Application backend.

#### Implemented Components:
- Domain Entities: Flight, AircraftType, Airport, Location.
- Use Cases: GetFlightsByAirport, FilterFlightsByAircraftType, AnalyzeDepartureFrequency.
- Gateway Interface: FlightDataGateway.
- Mock Gateway: MockFlightDataApiImpl.
- Repository Interface: FlightRepository.

#### Next Steps:
- Phase 3 will involve completing the API layer, database persistence, and architectural tests.

### Phase 3: API, Database Persistence, and Architectural Tests

In this phase, the Plane Spotter backend application has been completed with RESTful API endpoints, data persistence using an in-memory H2 database, and automated architectural validation via ArchUnit.

#### Implemented Components:
- **API Layer**: 
  - `FlightController`: Provides endpoints for fetching flights and analyzing departure frequency.
- **Presenters and DTOs**: 
  - `FlightDto`, `FrequencyDataPointDto` for data transfer.
  - `FlightPresenter`, `FrequencyGraphPresenter` for converting domain entities to DTOs.
- **Database Persistence**:
  - `FlightJpaEntity`, `FlightJpaRepository`, `FlightDatabaseImpl` to handle database operations.
- **Architectural Tests**:
  - `ArchitectureTest`: Ensures adherence to Clean Architecture principles using ArchUnit.

#### Running the Application:
- Launch the Spring Boot application with `mvn spring-boot:run`.
- Access the H2 console at `http://localhost:8080/h2-console` using JDBC URL `jdbc:h2:mem:testdb`.

#### API Usage:
- **Get Flights**: `GET /api/v1/flights?airport_code=FRA&aircraft_type=A320&type=departures`
- **Get Departure Frequency**: `GET /api/v1/flights/frequency?airport_code=FRA&aircraft_type=B777`

#### Running ArchUnit Tests:
- Run `mvn test` to execute the ArchUnit tests and validate architectural rules.

This phase completes the backend development, setting the stage for the next phase focusing on front-end integration and further enhancements.
