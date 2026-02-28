package com.fahimeh.rideready.data.local.cache

import com.fahimeh.rideready.data.local.dao.ForecastDao
import com.fahimeh.rideready.data.local.entity.ForecastCacheEntity
import com.fahimeh.rideready.data.remote.dto.ForecastResponseDto
import com.squareup.moshi.Moshi

/**
 * Lokale Datenquelle für Forecast-Cache.
 *
 * Speichert die letzte erfolgreiche API-Antwort als JSON,
 * damit die App auch ohne Internet Daten anzeigen kann.
 */
class ForecastCacheLocalDataSource(
    private val dao: ForecastDao,
    private val moshi: Moshi
) {
    private val adapter by lazy {
        moshi.adapter(ForecastResponseDto::class.java)
    }

    suspend fun save(key: String, dto: ForecastResponseDto) {
        val json = adapter.toJson(dto)

        dao.insertForecast(
            ForecastCacheEntity(
                cityName = key,
                rawJson = json,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun load(key: String): ForecastResponseDto? {
        val entity = dao.getForecastForCity(key) ?: return null
        return runCatching { adapter.fromJson(entity.rawJson) }.getOrNull()
    }
}