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

## Known Design Tradeoffs

- Storage is intentionally in-memory, so data is lost on restart.
- The AI service is mocked by a deterministic template generator. The interface is in place so a real LLM-backed implementation can be swapped in later.
- Prompt-injection protection is conservative and input-based. For a production integration, pair this with stronger prompt isolation, auditing, and output validation.

## Challenge Deliverables

- [AI_LOG.md](./AI_LOG.md)
- [SECURITY.md](./SECURITY.md)
