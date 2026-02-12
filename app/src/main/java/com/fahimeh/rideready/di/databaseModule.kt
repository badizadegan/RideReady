package com.fahimeh.rideready.di

import androidx.room.Room
import com.fahimeh.rideready.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin-Modul für lokale Datenbank (Room).
 *
 * Stellt Datenbank und DAOs bereit.
 */
val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "rideready_db"
        ).build()
    }

    single { get<AppDatabase>().cityDao() }
    single { get<AppDatabase>().forecastDao() }
}