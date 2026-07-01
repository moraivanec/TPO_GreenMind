package com.example.greenmind

import com.example.greenmind.components.mijardin.MiJardinScreenViewModel
import com.example.greenmind.data.PlantDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

// Prueba que al cargar Mi Jardin se muestren las plantas guardadas
// Y que al quitar una planta, se borre del listado

@OptIn(ExperimentalCoroutinesApi::class)
class MiJardinScreenViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `al cargar mi jardin muestra plantas guardadas`() = runTest {
        val savedPlants = mutableListOf(
            PlantDetail(
                id = 1,
                commonName = "Cactus",
                scientificName = "Cactaceae",
                description = "Planta resistente",
                watering = "Bajo",
                sunlight = "Sol directo",
                careLevel = "Easy",
                pruningMonth = "No informado",
                propagation = "Esqueje",
                cycle = "Perenne",
                imageUrl = ""
            )
        )

        val repo = FakePlantRepository(savedPlants = savedPlants)
        val vm = MiJardinScreenViewModel(repo)

        vm.loadSavedPlants()

        advanceUntilIdle()

        val state = vm.uiState.value

        assertFalse(state.isLoading)
        assertEquals(1, state.savedPlants.size)
        assertEquals("Cactus", state.savedPlants[0].commonName)
    }

    @Test
    fun `al quitar una planta se elimina de mi jardin`() = runTest {
        val savedPlants = mutableListOf(
            PlantDetail(
                id = 1,
                commonName = "Cactus",
                scientificName = "Cactaceae",
                description = "Planta resistente",
                watering = "Bajo",
                sunlight = "Sol directo",
                careLevel = "Easy",
                pruningMonth = "No informado",
                propagation = "Esqueje",
                cycle = "Perenne",
                imageUrl = ""
            )
        )

        val repo = FakePlantRepository(savedPlants = savedPlants)
        val vm = MiJardinScreenViewModel(repo)

        vm.loadSavedPlants()
        advanceUntilIdle()

        vm.removePlant(1)
        advanceUntilIdle()

        val state = vm.uiState.value

        assertEquals(0, state.savedPlants.size)
    }
}