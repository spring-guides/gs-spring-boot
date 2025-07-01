# Plane Spotter Application - Backend

## Phase 1: Project Structure Setup

This phase focuses on setting up the foundational project structure for the Plane Spotter Application backend using Java and Spring Boot, following Clean Architecture principles.

### Tasks Completed

1. **Repository Cloning and Cleanup**
   - Cloned the repository and removed all existing 'greeting' related code to start with a clean Spring Boot starter project.

2. **Dependencies Setup**
   - Updated `pom.xml` to include necessary dependencies:
     - `spring-boot-starter-web`
     - `spring-boot-starter-data-jpa`
     - `h2` database
     - `archunit-junit5` for architectural testing
     - `lombok` for boilerplate reduction
   - Configured Lombok Maven plugin for annotation processing.

3. **Package and Directory Structure**
   - Created the base package `com.planespotter` and relevant sub-packages for domain, application, and infrastructure layers.

4. **Java File Placeholders**
   - Created empty Java files with package declarations as placeholders for future implementation.

5. **H2 Database Configuration**
   - Configured `application.properties` with H2 database settings.

### Notes
- This phase does not include any functional code implementation.
- The project is set up to adhere to Clean Architecture principles, ensuring a clear separation of concerns.

### Next Phase
- The next phase will involve implementing the entities and use cases within the established structure.
