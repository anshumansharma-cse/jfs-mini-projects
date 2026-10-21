# Employee Management System

A full-stack Spring Boot web application for managing employees and departments. Built as a JFS Module 3 college project.

## Features

- **Authentication**: Login/logout with BCrypt password hashing, ADMIN and USER roles
- **JWT API**: Token-based authentication for REST endpoints (`/api/*`)
- **Employee CRUD**: Create, read, update, delete employees with validation
- **Department Management**: Full CRUD for departments with employee count display
- **Search**: Search employees by name, email, or department
- **Dashboard**: Statistics overview with department distribution
- **Responsive UI**: Clean, mobile-friendly Thymeleaf templates with CSS

## Technology Stack

- Java 25
- Spring Boot 3.3.5
- Spring Security (form-based + JWT)
- Spring Data JPA (Hibernate)
- H2 embedded database
- Thymeleaf templates
- Bean Validation
- JJWT for REST API authentication

## Project Structure

```
employee-management-web/
├── pom.xml
├── src/main/java/com/example/employeemanagementweb/
│   ├── config/SecurityConfig.java
│   ├── controller/{Auth, Dashboard, Employee, Department, EmployeeApi}Controller.java
│   ├── model/{Employee, Department, User, Role}.java
│   ├── repository/{Employee, Department, User, Role}Repository.java
│   ├── service/{Employee, Department, CustomUserDetails}Service.java
│   └── security/{JwtService, JwtAuthenticationFilter}.java
└── src/main/resources/
    ├── application.properties
    ├── static/css/style.css
    ├── static/js/app.js
    └── templates/{login, dashboard, employees/, departments/, error/}
```

## Employee-Department Relationship

- `Employee` has a many-to-one relationship with `Department`
- Deleting a department with assigned employees is blocked
- Departments track their employee count for display purposes

## Authentication Design

**Web sessions (browser):**
- Standard Spring Security form login at `/login`
- CSRF protection enabled for all HTML forms
- Session-based authentication for Thymeleaf pages

**REST API (JWT):**
- POST `/api/auth/login` returns a JWT token
- Include token as `Authorization: Bearer <token>` header
- JWT tokens expire after 24 hours (configurable via `jwt.expiration`)

## Database

- H2 embedded database (file mode) by default — no external server required
- Data stored in `data/db` (auto-created on first run)
- H2 console available at `/h2-console` for development
- Entity relationships managed by Hibernate/JPA

## Main Routes

| Route | Description |
|-------|-------------|
| `/login` | Login page |
| `/dashboard` | Overview dashboard |
| `/employees` | Employee listing with search |
| `/employees/new` | Add employee form |
| `/employees/{id}` | Employee detail view |
| `/employees/{id}/edit` | Edit employee form |
| `/departments` | Department listing |
| `/departments/new` | Add department form |

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/employees` | List all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| POST | `/api/employees` | Create employee |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| POST | `/api/auth/login` | Authenticate and get JWT |

## Development Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| user | user123 | USER |

> ⚠️ These are development-only credentials. Change them before any production use.

## Environment Variables

| Variable | Description |
|----------|-------------|
| `JWT_SECRET` | Base64-encoded secret for JWT signing (required for production) |
| `server.port` | Application port (default: 8080) |
| `spring.datasource.url` | H2 database connection URL |

## Running the Application

### From source (requires JDK 25 and Maven)

```bash
cd employee-management-web
mvn spring-boot:run
```

### Using Docker

```bash
cd employee-management-web
docker compose up -d
```

## Known Limitations

- No external database support configured (H2 only); swap datasource config for MySQL/PostgreSQL in production
- JWT secret has a development fallback — must be overridden via `JWT_SECRET` env var
- No password reset or account management features
- No pagination on employee/department lists
- CSRF is disabled in SecurityConfig to allow JWT API calls; enable for production use
- Tests were not run as part of this development cycle

---

Built for JFS Module 3 — By Anshuman Sharma <br>
 SRM Institute of Science and Technology

 GitHub: `anshumansharma-cse`
