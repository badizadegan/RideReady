package com.fahimeh.rideready.domain.model

import java.time.LocalDateTime

/**
 * Zeitfenster-Empfehlung für eine Aktivität.
 */
data class TimeWindow(
    val start: LocalDateTime,
    val end: LocalDateTime
)