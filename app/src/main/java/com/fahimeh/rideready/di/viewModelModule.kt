package com.fahimeh.rideready.di

import com.fahimeh.rideready.presentation.city.CityViewModel
import com.fahimeh.rideready.presentation.detail.DetailViewModel
import com.fahimeh.rideready.presentation.home.HomeViewModel
import com.fahimeh.rideready.presentation.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin-Modul für ViewModels.
 *
 * ViewModels werden hier zentral registriert.
 */
val viewModelModule = module {
    viewModel {
        HomeViewModel(
            getForecastUseCase = get(),
            findBestDayUseCase = get(),
            memoryStore = get(),
            observeSelectedCityUseCase = get()
        )
    }

    viewModel {
        DetailViewModel(
            memoryStore = get(),
            findBestTimeWindowUseCase = get(),
            observeSettingsUseCase = get()
        )
    }

    viewModel {
        CityViewModel(get(), get(), get(), get())
    }

    viewModel {
        SettingsViewModel(get(), get(), get())
    }
}