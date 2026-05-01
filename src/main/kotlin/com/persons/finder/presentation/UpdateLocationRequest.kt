package com.persons.finder.presentation

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin

data class UpdateLocationRequest(
    @field:Schema(example = "-36.8490")
    @field:DecimalMin("-90.0")
    @field:DecimalMax("90.0")
    val latitude: Double,
    @field:Schema(example = "174.7640")
    @field:DecimalMin("-180.0")
    @field:DecimalMax("180.0")
    val longitude: Double
)
