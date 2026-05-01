package com.persons.finder.domain.services

import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl : LocationsService {

    override fun distanceInKm(
        fromLatitude: Double,
        fromLongitude: Double,
        toLatitude: Double,
        toLongitude: Double
    ): Double {
        val earthRadiusKm = 6371.0
        val latDelta = Math.toRadians(toLatitude - fromLatitude)
        val lonDelta = Math.toRadians(toLongitude - fromLongitude)
        val startLat = Math.toRadians(fromLatitude)
        val endLat = Math.toRadians(toLatitude)

        val haversine = sin(latDelta / 2).pow(2) +
            cos(startLat) * cos(endLat) * sin(lonDelta / 2).pow(2)

        return 2 * earthRadiusKm * asin(sqrt(haversine))
    }
}
