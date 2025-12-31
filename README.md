# Lib Musical API

RESTful API developed for managing musical catalogs and user-centric music libraries.

## Technologies

* **Java 17**: Core language using Streams API, Optional, and Records.
* **Spring Boot 3**: Framework base.
* **Spring Data JPA**: Data persistence abstraction.
* **Bean Validation**: Metadata-driven data integrity.
* **Lombok**: Boilerplate code reduction.
* **H2/PostgreSQL**: Relational database support.

## Layered Architecture

The project follows a strict separation of concerns to ensure low coupling and high maintainability:

* **Models**: Domain entities representing database tables and Many-to-One relationships.
* **Repositories**: Interfaces extending JpaRepository for data access.
* **Services**: Business logic layer where functional data processing (Streams) occurs.
* **Controllers**: REST Endpoints management and Path Variable handling.
* **DTOs**: Data Transfer Objects for secure input (Request) and output (Response) handling.

## Setup and Execution

1. Ensure JDK 17 is installed and configured.
2. From the API root directory, run the Maven command:

```bash
mvn spring-boot:run
