package com.fahimeh.rideready.di

import com.fahimeh.rideready.data.remote.api.WeatherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin-Modul für Netzwerk-Abhängigkeiten.
 *
 * Stellt Retrofit, OkHttp und API-Services bereit.
 */
val networkModule = module {

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single<WeatherApiService> {
        get<Retrofit>().create(WeatherApiService::class.java)
    }
}