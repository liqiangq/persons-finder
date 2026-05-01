# Security Notes

## Prompt Injection Safeguard

The bio generation path does not send raw hobby or job-title text directly into the AI-style generator.

Before generating a bio, the service:

- removes known prompt-manipulation phrases such as `ignore all instructions`
- strips unsupported characters
- collapses whitespace
- truncates the prompt-facing text to a fixed length

This is implemented in `KeywordPromptInputSanitizer`. The sanitized values are only used for bio generation. The original user-facing values are still stored in the `Person` record.

## Privacy Risks

If this system sent `name` and `location` to a third-party LLM, the main risks would be:

- disclosure of personally identifiable information
- retention of sensitive data by the model provider
- cross-border data transfer or residency issues
- prompt and response logging in external systems

## Higher-Security Architecture

For a banking or other high-security environment, I would avoid sending raw customer identity and live location data to a third-party model.

Preferred approach:

- keep the LLM call out of the synchronous write path
- send only minimized, non-identifying attributes to the model
- tokenize or redact personal fields before prompt construction
- use an internal model or private deployment where possible
- log prompt payload versions, not raw PII
- add policy checks and output validation before persisting generated text

## Remaining Limits

This sanitizer is a practical baseline, not a full guarantee. A production system should also:

- isolate system instructions from user input structurally
- validate or regenerate suspicious outputs
- rate-limit abusive clients
- monitor prompt and output anomalies
