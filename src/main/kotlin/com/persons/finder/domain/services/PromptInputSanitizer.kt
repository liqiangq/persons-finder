package com.persons.finder.domain.services

interface PromptInputSanitizer {
    fun sanitize(rawInput: String): String
}
