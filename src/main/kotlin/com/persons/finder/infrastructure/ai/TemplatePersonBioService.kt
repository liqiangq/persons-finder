package com.persons.finder.infrastructure.ai

import com.persons.finder.domain.services.PersonBioService
import org.springframework.stereotype.Service

@Service
class TemplatePersonBioService : PersonBioService {
    override fun generateBio(jobTitle: String, hobbies: List<String>): String {
        val normalizedJobTitle = jobTitle.ifBlank { "mystery maker" }
        val hobbiesText = hobbies.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "curious side quests"

        return "$normalizedJobTitle by day, into $hobbiesText after hours, and rarely far from a good story."
    }
}
