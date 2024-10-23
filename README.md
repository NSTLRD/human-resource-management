# Human Resources Management Shop API

This project is a RESTful API for managing a human-resource-management, developed using Spring Boot. It includes functionality to manage users, Employee, admin, and provides authentication through JWT tokens. The project follows best practices for REST API development and includes integration with a Oracle database, OpenAPI for API documentation.

## Table of Contents

- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Installation and Setup](#installation-and-setup)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
- [Database Configuration](#database-configuration)
- [License](#license)

---

## Project Structure

- **src/main/java/com/mentorly/human-resource-management**
    - **controller**: Contains the REST controllers that handle HTTP requests and responses.
    - **dto**: Data Transfer Objects used for transferring data between layers.
    - **entity**: The entity classes representing the database tables.
    - **mapper**: Classes responsible for mapping between entities and DTOs.
    - **repository**: Contains the repository interfaces for database interactions using Spring Data JPA.
    - **service**: Service classes where the business logic is implemented.

- **src/main/resources**
    - Contains application configuration files such as `application.yml` for environment-specific settings.

- **src/test**
    - Contains unit and integration tests for the application.


- **Controller**: Handles the REST endpoints.
- **DTO**: Data Transfer Objects for the entities.
- **Entity**: Java classes mapped to database tables.
- **Mapper**: Converts entities to DTOs and vice versa (MapStruct).
- **Repository**: Interfaces for database operations using Spring Data JPA.
- **Service**: Business logic implementation.

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.0**
- **Spring Data JPA**
- **JWT (JSON Web Tokens)**
- **MapStruct 1.5.5.Final**
- **Spring Security**
- **OpenAPI 3.0** for API documentation
- **ORACLE** (for production database)
- **Lombok** (to reduce boilerplate code)

---

## Installation and Setup

### Prerequisites

- Java 17 or higher
- Maven 3.8.1 or higher
- Oracle (for production database)

### Setup Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/human-resource-management.git
   cd human-resource-management
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Configure the `application.yml` file for the database connection (see below for details).

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

---

## Running the Project

To run the project locally, use the embedded **H2** database. You can use the following commands:

1. **Build the project**:
   ```bash
   mvn clean package
   ```

2. **Run the project**:
   ```bash
   mvn spring-boot:run
   ```

3. **Access API Documentation** (OpenAPI Swagger UI):
   Open your browser and navigate to:
   ```
   http://localhost:8080/swagger-ui.html
   ```

---

## API Endpoints

### User Endpoints

| Method | Endpoint                    | Description             |
|--------|-----------------------------|-------------------------|
| GET    | `/api/v1/users`             | Get a list of all users |
| GET    | `/api/v1/users/{id}`        | Get an user by ID       |
| POST   | `/api/v1/register-user`     | Register a user         |
| PUT    | `/api/v1/update/{id}`       | Update an user by ID    |
| DELETE | `/api/v1/users-delete/{id}` | Delete an user by ID    |

## Database Configuration

### Oracle Setup (Production)

In the `src/main/resources/application.yml` file, configure the following for **MySQL**:

```yaml
spring:
  application:
    name: Human Resources Management API
  datasource:
    url: jdbc:oracle:thin:@localhost:1521:xe
    username: C##HUMAN_RESOURCE
    password: 1234
    driver-class-name: oracle.jdbc.OracleDriver

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.OracleDialect
        format_sql: true

  mail:
    host: localhost
    port: 1025
    username: admin
    password: admin
    properties:
      mail:
        smtp:
          trust: "*"
        auth: true
        starttls:
          enable: true
        connection:
          timeout: 5000
          writetimeout: 5000

security:
  jwt:
    secret-key: "RSjUBwnNFcjYzUFqFFDw1pCFbfZed5MC2QQVzs+CWeY="
    expiration-time: 86400000

mailing:
  frontend:
    activation:
      activationUrl:  http://localhost:4200/activate-account

logging:
  level:
    org:
      springframework:
        security: DEBUG
        web: DEBUG

server:
  port: 8080

```

# Contact and Social Links

- **GitHub**: [Starling Diaz](https://github.com/NSTLRD)
- **Website**: [Mentorly Blog](https://mentorly.blog/)
- **Linkedin**: [Linkedin](https://www.linkedin.com/in/starling-diaz-908225181/)
- **Mentorly Academy**: [Mentorly Academy](https://www.mentor-ly.com/)
- **Youtube**: [Mentorly Youtube](https://www.youtube.com/@Mentorly-e3b)
---

**Version:** 0.0.1-SNAPSHOT  
**Author:** Starling Diaz  
**License:** Open Source
```


### Explanation:

- The **Installation and Setup** section walks users through setting up the environment.
- The **API Endpoints** section details the available endpoints for `users` and hints at similar endpoints for `admin`.
- The **Database Configuration** section shows how to configure Oracle for production.
- The **Contact and Social Links** section provides links to the author's GitHub, website, LinkedIn, Mentorly Academy, and YouTube channel.
- The **License** section states that the project is open source.