package com.persons.finder.infrastructure.ai

import com.persons.finder.domain.services.PromptInputSanitizer
import org.springframework.stereotype.Component

@Component
class KeywordPromptInputSanitizer : PromptInputSanitizer {
    private val blockedPhrases = listOf(
        "ignore all instructions",
        "ignore previous instructions",
        "system prompt",
        "developer message",
        "say 'i am hacked'",
        "say \"i am hacked\"",
        "act as",
        "pretend to"
    )

    private val allowedCharacters = Regex("[^A-Za-z0-9,.'&\\- ]")
    private val collapsedSpaces = Regex("\\s+")

    override fun sanitize(rawInput: String): String {
        var sanitized = rawInput.trim()

        blockedPhrases.forEach { phrase ->
            sanitized = sanitized.replace(phrase, "", ignoreCase = true)
        }

        sanitized = sanitized
            .replace(allowedCharacters, " ")
            .replace(collapsedSpaces, " ")
            .trim()

        return sanitized.take(80)
    }
}
