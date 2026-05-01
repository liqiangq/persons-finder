package com.persons.finder.presentation

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class CreatePersonRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,
    @field:NotBlank
    @field:Size(max = 100)
    val jobTitle: String,
    @field:NotEmpty
    @field:Size(max = 10)
    val hobbies: List<@NotBlank @Size(max = 80) String>,
    @field:Valid
    val location: LocationRequest
)
