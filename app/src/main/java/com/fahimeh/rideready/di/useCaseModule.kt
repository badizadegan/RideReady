package com.fahimeh.rideready.di

import com.fahimeh.rideready.domain.usecase.CalculateRideScoreUseCase
import com.fahimeh.rideready.domain.usecase.DeleteCityUseCase
import com.fahimeh.rideready.domain.usecase.FindBestDayUseCase
import com.fahimeh.rideready.domain.usecase.FindBestTimeWindowUseCase
import com.fahimeh.rideready.domain.usecase.GetForecastUseCase
import com.fahimeh.rideready.domain.usecase.GetSavedCitiesUseCase
import com.fahimeh.rideready.domain.usecase.SaveCityUseCase
import org.koin.dsl.module

/**
 * Koin-Modul für UseCases.
 */
val useCaseModule = module {

    single { GetForecastUseCase(repository = get()) }

    single { CalculateRideScoreUseCase() }

    single { FindBestDayUseCase(calculateRideScoreUseCase = get()) }

    single { FindBestTimeWindowUseCase() }

    single { GetSavedCitiesUseCase(repository = get()) }
    single { SaveCityUseCase(repository = get()) }
    single { DeleteCityUseCase(repository = get()) }
}