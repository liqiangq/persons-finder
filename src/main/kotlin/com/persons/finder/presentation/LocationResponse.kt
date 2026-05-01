package com.persons.finder.presentation

import io.swagger.v3.oas.annotations.media.Schema

data class LocationResponse(
    @field:Schema(example = "-36.8485")
    val latitude: Double,
    @field:Schema(example = "174.7633")
    val longitude: Double
)
