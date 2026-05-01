package com.persons.finder.presentation

import com.persons.finder.domain.model.Person
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Person response")
data class PersonResponse(
    @field:Schema(example = "1")
    val id: Long,
    @field:Schema(example = "John Doe")
    val name: String,
    @field:Schema(example = "Backend Engineer")
    val jobTitle: String,
    val hobbies: List<String>,
    @field:Schema(example = "Backend Engineer by day, into coffee roasting, board games after hours, and rarely far from a good story.")
    val bio: String,
    val location: LocationResponse
) {
    companion object {
        fun from(person: Person): PersonResponse {
            return PersonResponse(
                id = person.id,
                name = person.name,
                jobTitle = person.jobTitle,
                hobbies = person.hobbies,
                bio = person.bio,
                location = LocationResponse(
                    latitude = person.location.latitude,
                    longitude = person.location.longitude
                )
            )
        }
    }
}
