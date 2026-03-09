package com.fahimeh.rideready.di

import com.fahimeh.rideready.domain.usecase.CalculateRideScoreUseCase
import com.fahimeh.rideready.domain.usecase.DeleteCityUseCase
import com.fahimeh.rideready.domain.usecase.FindBestDayUseCase
import com.fahimeh.rideready.domain.usecase.FindBestTimeWindowUseCase
import com.fahimeh.rideready.domain.usecase.GetForecastUseCase
import com.fahimeh.rideready.domain.usecase.GetSavedCitiesUseCase
import com.fahimeh.rideready.domain.usecase.GetSelectedCityUseCase
import com.fahimeh.rideready.domain.usecase.ObserveSelectedCityUseCase
import com.fahimeh.rideready.domain.usecase.ObserveSettingsUseCase
import com.fahimeh.rideready.domain.usecase.SaveCityUseCase
import com.fahimeh.rideready.domain.usecase.SelectCityUseCase
import com.fahimeh.rideready.domain.usecase.UpdateTemperatureUnitUseCase
import com.fahimeh.rideready.domain.usecase.UpdateTimeWindowHoursUseCase
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

    single { SelectCityUseCase(get()) }
    single { GetSelectedCityUseCase(get()) }

    single { ObserveSelectedCityUseCase(get()) }

    single { ObserveSettingsUseCase(get()) }
    single { UpdateTemperatureUnitUseCase(get()) }
    single { UpdateTimeWindowHoursUseCase(get()) }
}