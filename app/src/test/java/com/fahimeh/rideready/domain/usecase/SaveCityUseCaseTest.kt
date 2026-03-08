package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Testet das Speichern einer Stadt.
 */
class SaveCityUseCaseTest {

    private val repository = FakeCityRepository()
    private val useCase = SaveCityUseCase(repository)

    @Test
    fun savesCityIntoRepository() = runTest {
        val city = City(
            id = 1L,
            name = "Berlin",
            latitude = 52.52,
            longitude = 13.40,
            isSelected = false
        )

        useCase(city)

        val result = repository.observeCities().first()
        assertEquals(1, result.size)
        assertEquals("Berlin", result.first().name)
    }
}