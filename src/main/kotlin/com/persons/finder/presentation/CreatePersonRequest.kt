package com.persons.finder.presentation

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class CreatePersonRequest(
    @field:Schema(example = "John Doe", description = "Display name")
    @field:NotBlank
    @field:Size(max = 100)
    val name: String,
    @field:Schema(example = "Backend Engineer", description = "Current job title")
    @field:NotBlank
    @field:Size(max = 100)
    val jobTitle: String,
    @field:ArraySchema(
        schema = Schema(example = "coffee roasting"),
        arraySchema = Schema(description = "List of hobbies")
    )
    @field:NotEmpty
    @field:Size(max = 10)
    val hobbies: List<@NotBlank @Size(max = 80) String>,
    @field:Schema(description = "Initial location")
    @field:Valid
    val location: LocationRequest
)
