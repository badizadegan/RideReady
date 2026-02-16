package com.fahimeh.rideready.domain.model

/**
 * Ergebnis der Bewertungslogik für einen Tag.
 *
 * score: 0..100
 * reason: kurze Erklärung, warum dieser Score zustande kommt
 */
data class RideScoreResult(
    val score: Int,
    val reason: String
)