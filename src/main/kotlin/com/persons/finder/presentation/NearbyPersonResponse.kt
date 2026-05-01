package com.persons.finder.presentation

import com.persons.finder.domain.services.NearbyPerson
import io.swagger.v3.oas.annotations.media.Schema
import kotlin.math.round

@Schema(description = "Nearby person result")
data class NearbyPersonResponse(
    @field:Schema(example = "1")
    val id: Long,
    @field:Schema(example = "John Doe")
    val name: String,
    @field:Schema(example = "Backend Engineer")
    val jobTitle: String,
    val hobbies: List<String>,
    val bio: String,
    @field:Schema(example = "0.42")
    val distanceKm: Double,
    val location: LocationResponse
) {
    companion object {
        fun from(nearbyPerson: NearbyPerson): NearbyPersonResponse {
            val person = nearbyPerson.person

            return NearbyPersonResponse(
                id = person.id,
                name = person.name,
                jobTitle = person.jobTitle,
                hobbies = person.hobbies,
                bio = person.bio,
                distanceKm = round(nearbyPerson.distanceKm * 100.0) / 100.0,
                location = LocationResponse(
                    latitude = person.location.latitude,
                    longitude = person.location.longitude
                )
            )
        }
    }
}
