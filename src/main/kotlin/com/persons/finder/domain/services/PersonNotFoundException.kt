package com.persons.finder.domain.services

class PersonNotFoundException(id: Long) : RuntimeException("Person with id $id was not found")
