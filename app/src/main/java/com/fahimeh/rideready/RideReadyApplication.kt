package com.fahimeh.rideready

import android.app.Application
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
        }
    }
}