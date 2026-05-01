# Persons Finder

Runnable Kotlin/Spring Boot implementation of the backend challenge described in the original scaffold.

## What It Does

This service exposes three REST endpoints:

1. `POST /persons`
Creates a person with `name`, `jobTitle`, `hobbies`, and `location`.
It also generates a short bio through an AI-style service abstraction.

2. `PUT /persons/{id}/location`
Updates the stored location for an existing person.

3. `GET /persons/nearby?lat={lat}&lon={lon}&radiusKm={radius}`
Returns nearby persons sorted by distance from the query point.

## Implementation Notes

- Language: Kotlin
- Framework: Spring Boot
- Storage: In-memory repository backed by `ConcurrentHashMap`
- Distance calculation: Haversine formula
- AI integration: deterministic template-based implementation behind `PersonBioService`
- Prompt-injection safeguard: input sanitization before data is passed into bio generation

## Project Structure

- `presentation`: controllers, request/response DTOs, API exception handling
- `domain/model`: core `Person` and `Location` models
- `domain/services`: business logic interfaces and implementations
- `domain/repository`: repository contract
- `infrastructure/repository`: in-memory repository implementation
- `infrastructure/ai`: prompt sanitizer and mock bio generator

## API Examples

### Create a person

```bash
curl -X POST http://localhost:8080/persons \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "jobTitle": "Backend Engineer",
    "hobbies": ["coffee roasting", "board games"],
    "location": {
      "latitude": -36.8485,
      "longitude": 174.7633
    }
  }'
```

Example response:

```json
{
  "id": 1,
  "name": "John Doe",
  "jobTitle": "Backend Engineer",
  "hobbies": [
    "coffee roasting",
    "board games"
  ],
  "bio": "Backend Engineer by day, into coffee roasting, board games after hours, and rarely far from a good story.",
  "location": {
    "latitude": -36.8485,
    "longitude": 174.7633
  }
}
```

### Update a location

```bash
curl -X PUT http://localhost:8080/persons/1/location \
  -H "Content-Type: application/json" \
  -d '{
    "latitude": -36.8490,
    "longitude": 174.7640
  }'
```

### Find nearby persons

```bash
curl "http://localhost:8080/persons/nearby?lat=-36.8485&lon=174.7633&radiusKm=5"
```

## Validation Rules

- `name`: required, max 100 chars
- `jobTitle`: required, max 100 chars
- `hobbies`: required, 1 to 10 items, each max 80 chars
- `latitude`: between `-90` and `90`
- `longitude`: between `-180` and `180`
- `radiusKm`: must be greater than `0`

## Running Locally

### Prerequisites

- Java 11+
- Network access the first time Gradle downloads its distribution and dependencies

### Start the app

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

### Run tests

```bash
./gradlew test
```

## Deploy To Render

This app is compatible with a Render `Web Service`.

### Render settings

- Environment: `Java`
- Build Command: `./gradlew build`
- Start Command: `./gradlew bootRun`

The app is configured to bind to Render's `PORT` environment variable via:

```properties
server.port=${PORT:8080}
```

### Deploy steps

1. Push this repo to GitHub.
2. In Render, create `New` -> `Web Service`.
3. Connect the GitHub repository.
4. Set the build command to `./gradlew build`.
5. Set the start command to `./gradlew bootRun`.
6. Deploy.

### Recommended improvement for Render

`bootRun` works, but for production deployment it is usually better to run the built jar instead of the Gradle task.

You can switch to:

- Build Command: `./gradlew build`
- Start Command: `java -jar build/libs/PersonsFinder-0.0.1-SNAPSHOT.jar`

If you want, I can make that startup path more robust by configuring a stable jar name or adding a `render.yaml`.

## Known Design Tradeoffs

- Storage is intentionally in-memory, so data is lost on restart.
- The AI service is mocked by a deterministic template generator. The interface is in place so a real LLM-backed implementation can be swapped in later.
- Prompt-injection protection is conservative and input-based. For a production integration, pair this with stronger prompt isolation, auditing, and output validation.

## Challenge Deliverables

- [AI_LOG.md](./AI_LOG.md)
- [SECURITY.md](./SECURITY.md)
