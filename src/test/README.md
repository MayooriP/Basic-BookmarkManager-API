# Bookmark API Tests

This directory contains automated tests for the Bookmark API endpoints.

## Test Structure

- `com.bookmark.controller` - Controller tests for API endpoints
  - `CategoryControllerTest.java` - Tests for Category endpoints
  - `JournalControllerTest.java` - Tests for Journal endpoints
  - `ArticleControllerTest.java` - Tests for Article endpoints
- `com.bookmark.config` - Test configuration
  - `TestConfig.java` - Configuration for tests

## Running Tests

You can run the tests using the provided batch file:

```
run-api-tests.bat
```

Or using Maven directly:

```
mvn test -Dtest=CategoryControllerTest,JournalControllerTest,ArticleControllerTest
```

## Test Coverage

The tests cover all API endpoints:

### Category Endpoints
- GET /api/v1/categories - Get all categories
- POST /api/v1/categories - Create a new category
- GET /api/v1/categories/{id} - Get category by ID
- PUT /api/v1/categories/{id} - Update a category
- DELETE /api/v1/categories/{id} - Delete a category

### Journal Endpoints
- GET /api/v1/journals - Get all journals
- POST /api/v1/journals - Create a new journal
- GET /api/v1/journals/{id} - Get journal by ID
- PUT /api/v1/journals/{id} - Update a journal
- DELETE /api/v1/journals/{id} - Delete a journal

### Article Endpoints
- GET /api/v1/articles - Get all articles
- POST /api/v1/articles - Create a new article
- GET /api/v1/articles/{id} - Get article by ID
- PUT /api/v1/articles/{id} - Update an article
- DELETE /api/v1/articles/{id} - Delete an article

## Test Database

The tests use an in-memory H2 database instead of PostgreSQL to ensure tests are isolated and don't affect your production database.
