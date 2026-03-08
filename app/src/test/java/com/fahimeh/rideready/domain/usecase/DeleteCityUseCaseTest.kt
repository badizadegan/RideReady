package com.fahimeh.rideready.domain.usecase

import com.fahimeh.rideready.domain.model.City
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Testet das Löschen einer Stadt.
 */
class DeleteCityUseCaseTest {

    private val repository = FakeCityRepository()
    private val saveUseCase = SaveCityUseCase(repository)
    private val deleteUseCase = DeleteCityUseCase(repository)

    @Test
    fun deletesCityFromRepository() = runTest {
        val city = City(
            id = 1L,
            name = "Berlin",
            latitude = 52.52,
            longitude = 13.40,
            isSelected = false
        )

        saveUseCase(city)
        deleteUseCase(city)

        val result = repository.observeCities().first()
        assertEquals(0, result.size)
    }
}