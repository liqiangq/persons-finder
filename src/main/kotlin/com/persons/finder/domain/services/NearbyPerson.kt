package com.persons.finder.domain.services

import com.persons.finder.domain.model.Person

data class NearbyPerson(
    val person: Person,
    val distanceKm: Double
)
