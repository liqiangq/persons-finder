package com.persons.finder.domain.repository

import com.persons.finder.domain.model.Person

interface PersonRepository {
    fun save(person: Person): Person
    fun findById(id: Long): Person?
    fun findAll(): List<Person>
}
