package com.persons.finder.presentation

import com.persons.finder.domain.model.Location
import com.persons.finder.domain.services.PersonsService
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
    fun updateLocation(
        @PathVariable @Min(1) id: Long,
        @Valid @RequestBody request: UpdateLocationRequest
    ): PersonResponse {
        val person = personsService.updateLocation(id, request.toDomain())
        return PersonResponse.from(person)
    }

    @GetMapping("/nearby")
    fun getNearbyPersons(
        @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") lat: Double,
        @RequestParam @DecimalMin("-180.0") @DecimalMax("180.0") lon: Double,
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
