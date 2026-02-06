package com.fahimeh.rideready.core.result

import com.fahimeh.rideready.core.error.AppError

/**
 * Einheitlicher Wrapper für Ergebnisse aus Repository und UseCases.
 *
 * Ziel:
 * - Klare Trennung von Success / Error / Loading
 * - Vereinfachte Weitergabe von Zuständen an ViewModels
 * - Bessere Testbarkeit der Business-Logik
 */
sealed class AppResult<out T> {

    /**
     * Erfolgreiches Ergebnis mit Daten
     */
    data class Success<T>(
        val data: T
    ) : AppResult<T>()

    /**
     * Fehlerzustand mit AppError
     */
    data class Error(
        val error: AppError
    ) : AppResult<Nothing>()

    /**
     * Ladezustand (z. B. während eines API-Calls)
     */
    object Loading : AppResult<Nothing>()
}