# Plane Spotter Application

## Phase 1: Project Structure Setup (Revised - No POM Update)

In this phase, the project structure for the Plane Spotter Application backend has been set up using Java and Spring Boot. This setup includes a complete cleanup of the cloned repository and the establishment of the correct Clean Architecture directory hierarchy under the `com.planespotter` base package.

### Key Steps Performed
- Removed all original "greeting" related code.
- Created the necessary directory structure under `com.planespotter`.
- Added placeholder Java files with package declarations.
- Configured the H2 database for future use.

### Next Steps
In the next phase, the focus will be on implementing the entities and use cases as defined in the directory structure.


2. **Package and Directory Structure**
   - Created the base package `com.planespotter` and relevant sub-packages for domain, application, and infrastructure layers.
   - Removed any remnants of the old `com.example` package.

3. **Java File Placeholders**
   - Created empty Java files with package declarations as placeholders for future implementation.

4. **H2 Database Configuration**
   - Configured `application.properties` with H2 database settings.

### Notes
- This phase does not include any functional code implementation.
- The project is set up to adhere to Clean Architecture principles, ensuring a clear separation of concerns.

## Phase 2: Entities and Use Cases Implementation

### Objective
- Implement the core domain entities and application-specific use cases for the "Plane Spotter Application" backend.

### Tasks Completed

1. **Domain Entities**
   - Implemented `Flight`, `AircraftType`, `Airport`, and `Location` entities with Lombok annotations for brevity.

2. **Use Cases**
   - Developed use cases: `GetFlightsByAirport`, `FilterFlightsByAircraftType`, and `AnalyzeDepartureFrequency`.

3. **Infrastructure Setup**
   - Defined `FlightDataGateway` and `FlightRepository` interfaces.
   - Implemented `MockFlightDataApiImpl` for mock data provision.

### Next Phase
- The next phase will involve completing the API layer, database persistence, and architectural tests.