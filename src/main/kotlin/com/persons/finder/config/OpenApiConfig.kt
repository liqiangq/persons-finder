package com.persons.finder.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Persons Finder API",
        version = "1.0.0",
        description = "REST API for creating persons, updating locations, and finding nearby people.",
        contact = Contact(name = "Persons Finder")
    ),
    servers = [
        Server(url = "/", description = "Default server")
    ]
)
class OpenApiConfig
