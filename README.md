# Task Manager API

A simple REST API for managing tasks, built with Spring Boot. Supports full CRUD,
status filtering, and search — using an in-memory database so there's zero setup.

## Tech Stack
- Java 17
- Spring Boot 3.3.4
- Spring Data JPA (Hibernate)
- H2 in-memory database
- Maven

## How to run

**Requirements:** Java 17+ and Maven installed.

```bash
# 1. Navigate into the project
cd task-manager-api

# 2. Run it
mvn spring-boot:run
```

The app starts at `http://localhost:8080`.

You can also view the database directly in your browser at
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:taskdb`, username: `sa`, no password).

> No IDE? Open the folder in **IntelliJ IDEA** (Community Edition is free) and
> just click the green "Run" arrow on `TaskManagerApplication.java`.

## API Endpoints

| Method | Endpoint                          | Description                  |
|--------|------------------------------------|-------------------------------|
| GET    | `/api/tasks`                       | Get all tasks                |
| GET    | `/api/tasks?status=PENDING`        | Filter tasks by status        |
| GET    | `/api/tasks/{id}`                  | Get a single task by ID       |
| GET    | `/api/tasks/search?keyword=report` | Search tasks by title         |
| POST   | `/api/tasks`                       | Create a new task             |
| PUT    | `/api/tasks/{id}`                  | Update a task fully           |
| PATCH  | `/api/tasks/{id}/status?status=COMPLETED` | Update only the status |
| DELETE | `/api/tasks/{id}`                  | Delete a task                 |

Valid status values: `PENDING`, `IN_PROGRESS`, `COMPLETED`

## Example requests (using curl)

**Create a task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Finish LinkedIn profile", "description": "Add new projects"}'
```

**Get all tasks:**
```bash
curl http://localhost:8080/api/tasks
```

**Mark a task as completed:**
```bash
curl -X PATCH "http://localhost:8080/api/tasks/1/status?status=COMPLETED"
```

**Delete a task:**
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

You can also import these into **Postman** for easier testing with a UI.

## Project structure

```
src/main/java/com/tausif/taskmanager/
├── TaskManagerApplication.java   # entry point
├── model/
│   ├── Task.java                 # the entity (maps to DB table)
│   └── TaskStatus.java           # enum: PENDING, IN_PROGRESS, COMPLETED
├── repository/
│   └── TaskRepository.java       # data access layer (auto-implemented by Spring)
├── service/
│   └── TaskService.java          # business logic
├── controller/
│   └── TaskController.java       # REST API endpoints
└── exception/
    ├── TaskNotFoundException.java
    └── GlobalExceptionHandler.java  # converts errors into clean JSON
```

## Why it's structured this way

This follows the standard **layered architecture** used in real Spring Boot apps:

- **Controller** → handles HTTP requests/responses only
- **Service** → contains business logic, independent of HTTP
- **Repository** → talks to the database
- **Model** → represents your data

Keeping these separate makes the app easier to test, extend, and reason about —
this exact pattern is what you'll see in production codebases.

## Next steps to extend this project
- Add pagination (`Pageable` in Spring Data JPA)
- Add user accounts + JWT authentication so tasks belong to specific users
- Switch from H2 to PostgreSQL/MySQL for a "real" database
- Add unit tests with `@SpringBootTest` and `MockMvc`
- Deploy it on Render or Railway (free tiers) so it's live for your portfolio
