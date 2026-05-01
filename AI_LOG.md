# AI Log

## Interaction 1

I used AI to accelerate the Haversine-based nearby search implementation. The generated formula was directionally correct, but I checked the parameter order and distance units manually before keeping it.

## Interaction 2

I used AI to draft the REST surface and DTO structure for `POST /persons`, `PUT /persons/{id}/location`, and `GET /persons/nearby`. I then simplified it to an in-memory architecture because the original scaffold did not need a database to satisfy the challenge.

## Interaction 3

I used AI to sketch prompt-injection defenses for the bio generation flow. The first pass was too abstract, so I narrowed it to a concrete sanitizer plus tests proving malicious hobby text does not flow through into the generated bio.
