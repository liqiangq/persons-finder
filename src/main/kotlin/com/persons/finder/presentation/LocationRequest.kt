package com.persons.finder.presentation

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin

data class LocationRequest(
    @field:Schema(example = "-36.8485")
    @field:DecimalMin("-90.0")
    @field:DecimalMax("90.0")
    val latitude: Double,
    @field:Schema(example = "174.7633")
    @field:DecimalMin("-180.0")
    @field:DecimalMax("180.0")
    val longitude: Double
)
