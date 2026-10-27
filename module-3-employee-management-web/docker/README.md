# Docker Configuration for Employee Management System

## Prerequisites

Docker and Docker Compose must be installed on the host machine.

## Build and Run

```bash
# Build the Docker image
docker compose build

# Start the application
docker compose up -d

# View logs
docker compose logs -f

# Stop the application
docker compose down
```

## Access the Application

- Web UI: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:/data/db`
  - Username: `sa`
  - Password: (leave blank)

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `JWT_SECRET` | (required) | Secret key for JWT token signing. Must be set in production. |
| `JAVA_OPTS` | `-Xms256m -Xmx512m` | JVM memory settings |
| `SPRING_DATASOURCE_URL` | `jdbc:h2:file:/data/db` | H2 file-based database path |
| `SPRING_H2_CONSOLE_ENABLED` | `true` | Enable H2 web console |

## Data Persistence

The H2 database stores data in a Docker volume named `h2-data`. Data persists across container restarts. To reset the database:

```bash
docker compose down -v
docker compose up -d
```

## Health Check

The application exposes a health endpoint. Check status with:

```bash
docker compose ps
```
