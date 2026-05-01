package com.persons.finder.domain.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LocationsServiceImplTest {
    private val locationsService = LocationsServiceImpl()

    @Test
    fun `distance is zero for the same coordinates`() {
        val distance = locationsService.distanceInKm(
            fromLatitude = -36.8485,
            fromLongitude = 174.7633,
            toLatitude = -36.8485,
            toLongitude = 174.7633
        )

        assertEquals(0.0, distance, 0.000001)
    }

    @Test
    fun `distance matches expected haversine result approximately`() {
        val distance = locationsService.distanceInKm(
            fromLatitude = -36.8485,
            fromLongitude = 174.7633,
            toLatitude = -36.8500,
            toLongitude = 174.7650
        )

        assertTrue(distance in 0.20..0.30)
    }
}
