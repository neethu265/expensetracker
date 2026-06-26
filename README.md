# Expense Tracker

A Spring Boot REST API for tracking expenses with JWT authentication, role-based authorization, PostgreSQL persistence, validation, caching, and dashboard summaries.

## Tech Stack

- Java 21
- Spring Boot 4.1
- Spring Web MVC, Spring Security, Spring Data JPA
- PostgreSQL 16
- Maven Wrapper
- JUnit and Mockito for tests

## Project Structure

```text
src/main/java/com/example/expensetracker
+-- config/       # Spring Security configuration
+-- controller/   # REST endpoints
+-- dto/          # Request and response payloads
+-- entity/       # JPA entities
+-- exception/    # Custom exceptions and global handler
+-- repository/   # Spring Data repositories
+-- security/     # JWT filter
+-- service/      # Business logic
```

## Prerequisites

- JDK 21
- Docker Desktop, for local PostgreSQL
- PowerShell or a terminal that can run the Maven wrapper

## Run Locally

Start PostgreSQL:

```powershell
docker compose up -d
```

Run the API:

```powershell
.\mvnw.cmd spring-boot:run
```

The app uses `jdbc:postgresql://localhost:5433/expense_db` with username `postgres` and password `admin`.

## Testing

Run all tests:

```powershell
.\mvnw.cmd test
```

Run only the expense service tests:

```powershell
.\mvnw.cmd -Dtest=ExpenseServiceImplTest test
```

## Authentication

Register a user:

```http
POST /auth/register
Content-Type: application/json

{
  "username": "user1",
  "password": "password",
  "role": "ROLE_USER"
}
```

Login to receive a JWT:

```http
POST /auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password"
}
```

For protected routes, send:

```http
Authorization: Bearer <token>
```

## Expense API

Base path: `/api/expenses`

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/api/expenses` | Create an expense |
| `GET` | `/api/expenses` | List all expenses |
| `GET` | `/api/expenses/{id}` | Get one expense |
| `PUT` | `/api/expenses/{id}` | Replace title, amount, and category |
| `PATCH` | `/api/expenses/{id}/category` | Update only the category |
| `DELETE` | `/api/expenses/{id}` | Delete an expense |
| `GET` | `/api/expenses/category/{category}` | Filter by category |
| `GET` | `/api/expenses/dashboard` | Get total, average, and count |
| `GET` | `/api/expenses/offset?offset=0&limit=10` | Offset-based listing |

Create or update request:

```json
{
  "title": "Lunch",
  "amount": 150.0,
  "category": "Food"
}
```

Category update request:

```json
{
  "category": "Travel"
}
```

## Notes

- `/auth/register` and `/auth/login` are public.
- `/admin/**` requires `ROLE_ADMIN`.
- `/user/**` allows `ROLE_USER` or `ROLE_ADMIN`.
- All other routes require a valid JWT.

## Admin User Management

Admin routes require a JWT for a user with `ROLE_ADMIN`. The seeded local admin is `neethu` / `neethu123`.

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/admin/users` | List all users |
| `GET` | `/admin/users/{id}` | Get one user |
| `POST` | `/admin/users` | Create a user |
| `PUT` | `/admin/users/{id}` | Update username, role, and optionally password |
| `DELETE` | `/admin/users/{id}` | Delete a user and their expenses |

Create user request:

```json
{
  "username": "admin2",
  "password": "secret",
  "role": "ROLE_ADMIN"
}
```

Update user request:

```json
{
  "username": "user1",
  "password": "new-password",
  "role": "ROLE_USER"
}
```
