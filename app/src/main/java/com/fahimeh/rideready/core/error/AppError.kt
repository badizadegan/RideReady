package com.fahimeh.rideready.core.error

/**
 * Zentrale Fehlerdefinition für die App.
 *
 * Diese Klasse kapselt alle relevanten Fehlertypen,
 * damit UI und ViewModel nicht mit technischen Exceptions arbeiten müssen.
 */
sealed class AppError {

    /**
     * Netzwerkfehler (z. B. kein Internet)
     */
    object Network : AppError()

    /**
     * Serverfehler (z. B. 5xx)
     */
    object Server : AppError()

    /**
     * Unerwarteter Fehler
     */
    data class Unknown(
        val message: String? = null
    ) : AppError()
}
