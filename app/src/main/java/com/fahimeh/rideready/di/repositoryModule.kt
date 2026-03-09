package com.fahimeh.rideready.di

import com.fahimeh.rideready.core.forecast.ForecastMemoryStore
import com.fahimeh.rideready.data.repository.CityRepositoryImpl
import com.fahimeh.rideready.data.repository.SettingsRepositoryImpl
import com.fahimeh.rideready.data.repository.WeatherRepositoryImpl
import com.fahimeh.rideready.domain.repository.CityRepository
import com.fahimeh.rideready.domain.repository.SettingsRepository
import com.fahimeh.rideready.domain.repository.WeatherRepository
import org.koin.dsl.module

/**
 * Koin-Modul für Repositories.
 *
 * Bindet Repository-Interfaces an Implementierungen.
 */
val repositoryModule = module {

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            apiService = get(),
            cache = get()
        )
    }

    single<CityRepository> {
        CityRepositoryImpl(dao = get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(context = get())
    }

    single { ForecastMemoryStore() }
}