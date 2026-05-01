package com.persons.finder.presentation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `create update and query nearby persons`() {
        mockMvc.perform(
            post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "name": "John Doe",
                      "jobTitle": "Backend Engineer",
                      "hobbies": ["coffee roasting", "board games"],
                      "location": {
                        "latitude": -36.8485,
                        "longitude": 174.7633
                      }
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.bio").exists())

        mockMvc.perform(
            put("/persons/1/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "latitude": -36.8490,
                      "longitude": 174.7640
                    }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.location.latitude").value(-36.8490))

        mockMvc.perform(
            get("/persons/nearby")
                .param("lat", "-36.8485")
                .param("lon", "174.7633")
                .param("radiusKm", "2.0")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].name").value("John Doe"))
    }
}
