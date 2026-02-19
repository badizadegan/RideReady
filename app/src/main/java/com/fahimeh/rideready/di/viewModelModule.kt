package com.fahimeh.rideready.di

import com.fahimeh.rideready.presentation.home.HomeViewModel
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
            getForecastUseCase = get()
        )
    }
}