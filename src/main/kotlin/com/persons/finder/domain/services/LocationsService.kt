package com.persons.finder.domain.services

interface LocationsService {
    fun distanceInKm(
        fromLatitude: Double,
        fromLongitude: Double,
        toLatitude: Double,
        toLongitude: Double
    ): Double
}
