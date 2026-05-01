package com.persons.finder.presentation

import com.persons.finder.domain.model.Person

data class PersonResponse(
    val id: Long,
    val name: String,
    val jobTitle: String,
    val hobbies: List<String>,
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
