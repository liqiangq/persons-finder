package com.persons.finder.presentation

import com.persons.finder.domain.model.Location
import com.persons.finder.domain.services.PersonsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/persons")
class PersonController(
    private val personsService: PersonsService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a person",
        description = "Creates a person record and generates a short quirky bio from the job title and hobbies."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Person created"),
            ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()])
        ]
    )
    fun createPerson(@Valid @RequestBody request: CreatePersonRequest): PersonResponse {
        val person = personsService.createPerson(
            name = request.name,
            jobTitle = request.jobTitle,
            hobbies = request.hobbies,
            location = request.location.toDomain()
        )

        return PersonResponse.from(person)
    }

    @PutMapping("/{id}/location")
    @Operation(
        summary = "Update a person's location",
        description = "Replaces the stored latitude and longitude for an existing person."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Location updated"),
            ApiResponse(responseCode = "400", description = "Invalid request body", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Person not found", content = [Content()])
        ]
    )
    fun updateLocation(
        @Parameter(description = "Person id", example = "1")
        @PathVariable @Min(1) id: Long,
        @Valid @RequestBody request: UpdateLocationRequest
    ): PersonResponse {
        val person = personsService.updateLocation(id, request.toDomain())
        return PersonResponse.from(person)
    }

    @GetMapping("/nearby")
    @Operation(
        summary = "Find nearby persons",
        description = "Returns persons within the given radius in kilometers, sorted by distance ascending."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Nearby persons returned"),
            ApiResponse(responseCode = "400", description = "Invalid query parameters", content = [Content()])
        ]
    )
    fun getNearbyPersons(
        @Parameter(description = "Query latitude", example = "-36.8485")
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") lat: Double,
        @Parameter(description = "Query longitude", example = "174.7633")
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") lon: Double,
        @Parameter(description = "Radius in kilometers", example = "5")
        @RequestParam @DecimalMin(value = "0.0", inclusive = false) radiusKm: Double
    ): List<NearbyPersonResponse> {
        return personsService.findNearby(lat, lon, radiusKm)
            .map(NearbyPersonResponse::from)
    }
}

private fun LocationRequest.toDomain(): Location = Location(
    latitude = latitude,
    longitude = longitude
)

private fun UpdateLocationRequest.toDomain(): Location = Location(
    latitude = latitude,
    longitude = longitude
)
