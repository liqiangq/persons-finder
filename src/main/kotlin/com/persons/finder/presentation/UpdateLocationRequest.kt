package com.persons.finder.presentation

import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin

data class UpdateLocationRequest(
    @field:DecimalMin("-90.0")
    @field:DecimalMax("90.0")
    val latitude: Double,
    @field:DecimalMin("-180.0")
    @field:DecimalMax("180.0")
    val longitude: Double
)
