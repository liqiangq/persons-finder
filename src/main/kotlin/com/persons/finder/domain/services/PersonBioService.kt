package com.persons.finder.domain.services

interface PersonBioService {
    fun generateBio(jobTitle: String, hobbies: List<String>): String
}
