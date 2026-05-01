package com.persons.finder.infrastructure.repository

import com.persons.finder.domain.model.Person
import com.persons.finder.domain.repository.PersonRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import org.springframework.stereotype.Repository

@Repository
class InMemoryPersonRepository : PersonRepository {
    private val idSequence = AtomicLong(1)
    private val storage = ConcurrentHashMap<Long, Person>()

    override fun save(person: Person): Person {
        val persistedPerson = if (person.id == 0L) {
            person.copy(id = idSequence.getAndIncrement())
        } else {
            person
        }

        storage[persistedPerson.id] = persistedPerson
        return persistedPerson
    }

    override fun findById(id: Long): Person? = storage[id]

    override fun findAll(): List<Person> = storage.values.toList()
}
