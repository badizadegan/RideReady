package com.fahimeh.rideready

import android.app.Application
import com.fahimeh.rideready.di.databaseModule
import com.fahimeh.rideready.di.networkModule
import com.fahimeh.rideready.di.repositoryModule
import com.fahimeh.rideready.di.useCaseModule
import com.fahimeh.rideready.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application-Klasse zum Starten von Koin.
 */
class RideReadyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RideReadyApplication)
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}