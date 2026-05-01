package com.persons.finder.presentation

import com.persons.finder.domain.services.NearbyPerson
import kotlin.math.round

data class NearbyPersonResponse(
    val id: Long,
    val name: String,
    val jobTitle: String,
    val hobbies: List<String>,
    val bio: String,
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
