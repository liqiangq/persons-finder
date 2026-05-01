package com.persons.finder.domain.services

import com.persons.finder.domain.model.Location
import com.persons.finder.domain.model.Person
import com.persons.finder.domain.repository.PersonRepository
import org.springframework.stereotype.Service

@Service
class PersonsServiceImpl(
    private val personRepository: PersonRepository,
    private val aiBioService: PersonBioService,
    private val promptInputSanitizer: PromptInputSanitizer,
    private val locationsService: LocationsService
) : PersonsService {

    override fun createPerson(
        name: String,
        jobTitle: String,
        hobbies: List<String>,
        location: Location
    ): Person {
        val normalizedName = name.trim()
        val normalizedJobTitle = jobTitle.trim()
        val normalizedHobbies = hobbies.map(String::trim).filter { it.isNotBlank() }
        val sanitizedJobTitle = promptInputSanitizer.sanitize(jobTitle)
        val sanitizedHobbies = hobbies.map(promptInputSanitizer::sanitize).filter { it.isNotBlank() }
        val bio = aiBioService.generateBio(sanitizedJobTitle, sanitizedHobbies)

        return personRepository.save(
            Person(
                id = 0L,
                name = normalizedName,
                jobTitle = normalizedJobTitle,
                hobbies = normalizedHobbies,
                bio = bio,
                location = location
            )
        )
    }

    override fun updateLocation(id: Long, location: Location): Person {
        val existingPerson = getById(id)
        val updatedPerson = existingPerson.copy(location = location)
        return personRepository.save(updatedPerson)
    }

    override fun findNearby(latitude: Double, longitude: Double, radiusInKm: Double): List<NearbyPerson> {
        return personRepository.findAll()
            .map { person ->
                NearbyPerson(
                    person = person,
                    distanceKm = locationsService.distanceInKm(
                        fromLatitude = latitude,
                        fromLongitude = longitude,
                        toLatitude = person.location.latitude,
                        toLongitude = person.location.longitude
                    )
                )
            }
            .filter { it.distanceKm <= radiusInKm }
            .sortedBy { it.distanceKm }
    }

    override fun getById(id: Long): Person {
        return personRepository.findById(id)
            ?: throw PersonNotFoundException(id)
    }
}
