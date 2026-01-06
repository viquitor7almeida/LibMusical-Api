# Lib Musical API

RESTful API developed for managing musical catalogs and user-centric music libraries.

## Technologies

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **Bean Validation**
* **Lombok**

## Layered Architecture

The project follows a strict separation of concerns to ensure low coupling and high maintainability:

* **Models**: Domain entities representing database tables and Many-to-One relationships.
* **Repositories**: Interfaces extending JpaRepository for data access.
* **Services**: Business logic layer where functional data processing (Streams) occurs.
* **Controllers**: REST Endpoints management and Path Variable handling.
* **DTOs**: Data Transfer Objects for secure input (Request) and output (Response) handling.


## DB Diagram
<img width="813" height="386" alt="lib-musical-diagram" src="https://github.com/user-attachments/assets/ed2b5aa4-320c-4b79-85cd-48b0ce0214a2" />
---

## Testing Tutorial

To run and test the **Lib Musical API**, follow these steps:

### 1. Prerequisites
* **JDK 17**: Ensure you have Java Development Kit 17 installed.
* **Maven**: Ensure Apache Maven is installed and configured in your system's PATH.

### 2. Database Initialization
* The system is configured with **Spring Data JPA**.
* You do not need to create tables manually; the database schema will be **automatically generated** based on the project **Models** as soon as the application starts.

### 3. Running the Application
Open your terminal in the root directory and execute:
```bash
mvn spring-boot:run
```

### 4. Authentication and Access

The API implements security constraints for most endpoints:

* **Public Endpoints**: Only `/user` and `/auth` routes are accessible without a token.
* **Protected Endpoints**: For all other musical catalog management routes, you must first authenticate.

**How to Authenticate:**
1. Send a request to the `/auth/login` endpoint with your credentials.
2. Use the returned token (JWT) in the header of your subsequent requests to access protected data.



