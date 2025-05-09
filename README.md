# Bookmark API

A Spring Boot application for managing bookmarks with categories, journals, and articles.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Test Results](#test-results)
- [Features](#features)

## Technologies Used

- Java 17
- Spring Boot 3.4.2
- Spring Data JPA
- PostgreSQL
- H2 Database (for testing)
- Lombok
- MapStruct
- Flyway (for database migrations)
- JUnit 5 & Mockito (for testing)

## Project Structure

The application follows a standard Spring Boot project structure:

- `src/main/java/com/bookmark`: Main source code
  - `controller`: REST controllers
  - `dto`: Data Transfer Objects
  - `entity`: JPA entities
  - `exception`: Custom exceptions and global exception handler
  - `mapper`: MapStruct mappers
  - `repository`: Spring Data JPA repositories
  - `service`: Business logic
- `src/main/resources`: Configuration files
- `src/test/java/com/bookmark`: Test code
  - `controller`: Controller tests
  - `exception`: Exception tests

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL (configured on port 5433)

### Database Configuration

The application is configured to use PostgreSQL with the following settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/Bookmarkapp
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
server.port=8082
```

### Building the Application

To build the application, run:

```bash
cd bookmark
mvn clean package
```

Or simply double-click the `build-and-run.bat` file.

## Running the Application

### Using Batch File

Double-click `build-and-run.bat` to build and start the application.

### Using Maven

```bash
cd bookmark
mvn spring-boot:run
```

### Using Java

```bash
cd bookmark
java -jar target/bookmark-0.0.1-SNAPSHOT.jar
```

The application will start on port 8082. You can access it at [http://localhost:8082](http://localhost:8082)

## API Endpoints

The API follows RESTful conventions with a base path of `/api/v1`.

### Categories

| Method | Endpoint | Description |
|--------|----------|--------------|
| GET | `/api/v1/categories` | Get all categories |
| GET | `/api/v1/categories/{id}` | Get category by ID |
| POST | `/api/v1/categories` | Create a new category |
| PUT | `/api/v1/categories/{id}` | Update a category |
| DELETE | `/api/v1/categories/{id}` | Delete a category |

#### Example Request (Create Category)

```bash
curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Technology\",\"description\":\"Articles on technology\"}" http://localhost:8082/api/v1/categories
```

### Journals

| Method | Endpoint | Description |
|--------|----------|--------------|
| GET | `/api/v1/journals` | Get all journals |
| GET | `/api/v1/journals/{id}` | Get journal by ID |
| POST | `/api/v1/journals` | Create a new journal |
| PUT | `/api/v1/journals/{id}` | Update a journal |
| DELETE | `/api/v1/journals/{id}` | Delete a journal |

#### Example Request (Create Journal)

```bash
curl -X POST -H "Content-Type: application/json" -d "{\"year\":2024,\"title\":\"Journal of Science\",\"description\":\"Scientific research\"}" http://localhost:8082/api/v1/journals
```

### Articles

| Method | Endpoint | Description |
|--------|----------|--------------|
| GET | `/api/v1/articles` | Get all articles |
| GET | `/api/v1/articles/{id}` | Get article by ID |
| POST | `/api/v1/articles` | Create a new article |
| PUT | `/api/v1/articles/{id}` | Update an article |
| DELETE | `/api/v1/articles/{id}` | Delete an article |

#### Example Request (Create Article)

```bash
curl -X POST -H "Content-Type: application/json" -d "{\"title\":\"New Scientific Discovery\",\"subtitle\":\"Breakthrough research\",\"content\":\"Content about scientific discovery...\",\"author\":\"Robert Green\",\"categoryId\":1,\"journalId\":1}" http://localhost:8082/api/v1/articles
```

## Testing

The application includes comprehensive tests for all API endpoints. Tests use:

- MockMvc for simulating HTTP requests
- Mockito for mocking service dependencies
- H2 in-memory database for integration tests

### Running Tests

To run all tests:

```bash
cd bookmark
mvn test
```

Or simply double-click the `run-api-tests.bat` file.

To run specific controller tests:

```bash
mvn test -Dtest=CategoryControllerTest
mvn test -Dtest=JournalControllerTest
mvn test -Dtest=ArticleControllerTest
```

## Test Results

All tests have been executed successfully. Here's a summary of the test results:

### CategoryControllerTest (5 tests)

| Test | Description | Result |
|------|-------------|--------|
| testGetAllCategories | Tests GET /api/v1/categories endpoint | PASSED ✅ |
| testAddCategory | Tests POST /api/v1/categories endpoint | PASSED ✅ |
| testGetCategoryById | Tests GET /api/v1/categories/{id} endpoint | PASSED ✅ |
| testUpdateCategory | Tests PUT /api/v1/categories/{id} endpoint | PASSED ✅ |
| testDeleteCategory | Tests DELETE /api/v1/categories/{id} endpoint | PASSED ✅ |

### JournalControllerTest (5 tests)

| Test | Description | Result |
|------|-------------|--------|
| testGetAllJournals | Tests GET /api/v1/journals endpoint | PASSED ✅ |
| testAddJournal | Tests POST /api/v1/journals endpoint | PASSED ✅ |
| testGetJournalById | Tests GET /api/v1/journals/{id} endpoint | PASSED ✅ |
| testUpdateJournal | Tests PUT /api/v1/journals/{id} endpoint | PASSED ✅ |
| testDeleteJournal | Tests DELETE /api/v1/journals/{id} endpoint | PASSED ✅ |

### ArticleControllerTest (5 tests)

| Test | Description | Result |
|------|-------------|--------|
| testGetAllArticles | Tests GET /api/v1/articles endpoint | PASSED ✅ |
| testAddArticle | Tests POST /api/v1/articles endpoint | PASSED ✅ |
| testGetArticleById | Tests GET /api/v1/articles/{id} endpoint | PASSED ✅ |
| testUpdateArticle | Tests PUT /api/v1/articles/{id} endpoint | PASSED ✅ |
| testDeleteArticle | Tests DELETE /api/v1/articles/{id} endpoint | PASSED ✅ |

### Exception Tests

| Test | Description | Result |
|------|-------------|--------|
| ResourceNotFoundExceptionTest | Tests ResourceNotFoundException | PASSED ✅ |
| GlobalExceptionHandlerTest | Tests GlobalExceptionHandler | PASSED ✅ |

### Overall Test Results

```text
Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
```

## Features

- CRUD operations for categories, journals, and articles
- Validation for all inputs
- Global exception handling
- Consistent API response format
- Category names are unique
- Comprehensive test coverage
