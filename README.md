# Plane Spotter Application

## Phase 2: Entities and Use Cases Implementation

In this phase, we have implemented the core domain entities and application-specific use cases for the Plane Spotter Application backend.

### Implemented Features

- **Domain Entities**: Defined core entities such as Flight, AircraftType, Airport, and Location using Lombok annotations for brevity.
- **Use Cases**: Developed application-specific use cases including GetFlightsByAirport, FilterFlightsByAircraftType, and AnalyzeDepartureFrequency using Spring's `@Service` annotation.
- **Data Gateways**: Implemented a mock data gateway (MockFlightDataApiImpl) that provides static flight data for testing purposes.
- **FlightRepository Interface**: Defined the interface for future database interactions.

### Next Steps

The next phase will involve completing the API layer, database persistence, and architectural tests. Stay tuned for further updates.
