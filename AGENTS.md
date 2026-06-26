# Repository Guidelines

## Project Structure & Module Organization

This is a Java 21 Spring Boot expense tracker using Maven. Application code lives under `src/main/java/com/example/expensetracker`.

- `controller/`: REST endpoints for auth, users, expenses, admin, and test routes.
- `service/` and `service/impl/`: business logic, JWT handling, and user details integration.
- `repository/`: Spring Data JPA repositories.
- `entity/`: persisted JPA models such as `User` and `Expense`.
- `dto/`: request and response objects used at API boundaries.
- `config/`, `security/`, and `exception/`: Spring Security, JWT filter, and global error handling.
- `src/main/resources/application.properties`: local application and datasource settings.
- `src/test/java/com/example/expensetracker`: JUnit/Spring tests.

Build output is generated in `target/` and should not be edited directly.

## Build, Test, and Development Commands

Use the Maven wrapper so contributors run the same Maven version:

- `.\mvnw.cmd spring-boot:run`: start the API locally.
- `.\mvnw.cmd test`: run the test suite.
- `.\mvnw.cmd clean package`: compile, test, and create the packaged artifact in `target/`.
- `docker compose up -d`: start the local PostgreSQL 16 database defined in `docker-compose.yml` on host port `5433`.

The default local database is `expense_db` with user `postgres` and password `admin`.

## Coding Style & Naming Conventions

Follow standard Java and Spring conventions: 4-space indentation, PascalCase class names, camelCase fields and methods, and package names in lowercase. Keep controllers thin; put business rules in services and persistence queries in repositories. Prefer DTOs for request/response payloads instead of exposing entities from controllers. Lombok is available, but use it consistently and keep generated behavior obvious.

## Testing Guidelines

Tests use Spring Boot's test stack with JUnit. Place tests in the matching package under `src/test/java`. Name tests after the class under test, such as `ExpenseServiceImplTest`, and reserve `*ApplicationTests` for context-level checks. Run `.\mvnw.cmd test` before opening a pull request. Add coverage for service logic, security-sensitive behavior, and exception handling.

## Commit & Pull Request Guidelines

Recent history uses short, imperative summaries such as `Implemented JWT authentication and role-based authorization`. Keep commits focused and describe the behavior changed. Avoid vague messages like `changes`.

Pull requests should include a concise description, linked issue if available, test results, and example requests or screenshots for API/UI-visible behavior. Mention any configuration or database changes explicitly.

## Security & Configuration Tips

Do not commit real secrets. Local credentials in `application.properties` and `docker-compose.yml` are development defaults only. When changing authentication, authorization, or JWT code, document expected roles and token behavior in the PR.
