package com.fahimeh.rideready.di

import com.fahimeh.rideready.domain.usecase.GetForecastUseCase
import org.koin.dsl.module

/**
 * Koin-Modul für UseCases.
 */
val useCaseModule = module {

    single {
        GetForecastUseCase(repository = get())
    }
}