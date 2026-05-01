package com.persons.finder.domain.services

import com.persons.finder.domain.model.Location
import com.persons.finder.domain.model.Person

interface PersonsService {
    fun createPerson(
        name: String,
        jobTitle: String,
        hobbies: List<String>,
        location: Location
    ): Person

    fun updateLocation(id: Long, location: Location): Person

    fun findNearby(latitude: Double, longitude: Double, radiusInKm: Double): List<NearbyPerson>

    fun getById(id: Long): Person
}
