package com.fahimeh.rideready.di

import com.fahimeh.rideready.data.remote.api.GeocodingApiService
import com.fahimeh.rideready.data.remote.api.WeatherApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
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
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single(named("geocodingRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<WeatherApiService> {
        get<Retrofit>().create(WeatherApiService::class.java)
    }

    single<GeocodingApiService> {
        get<Retrofit>(named("geocodingRetrofit")).create(GeocodingApiService::class.java)
    }
}
