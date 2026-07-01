package com.example.greenmind

import com.example.greenmind.components.plantdetail.PlantDetailScreenViewModel
import com.example.greenmind.data.PlantDetail
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
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

// Prueba cargar el detalle de una planta, guardar una planta en Mi Jardin,
// quitar una planta guardada y limpiar el mensaje de guardado o eliminación

@OptIn(ExperimentalCoroutinesApi::class)
class PlantDetailScreenViewModelTest {

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
    fun `al cargar detalle muestra la planta seleccionada`() = runTest {
        val repo = FakePlantRepository()
        val vm = PlantDetailScreenViewModel(repo)

        vm.setPlantId(1)

        advanceUntilIdle()

        val state = vm.uiState.value

        assertFalse(state.isLoading)
        assertNotNull(state.plant)
        assertEquals(1, state.plant?.id)
        assertEquals("Planta 1", state.plant?.commonName)
        assertFalse(state.isSaved)
        assertNull(state.errorMessage)
    }

    @Test
    fun `al guardar una planta actualiza estado a guardada`() = runTest {
        val repo = FakePlantRepository()
        val vm = PlantDetailScreenViewModel(repo)

        vm.setPlantId(1)
        advanceUntilIdle()

        vm.togglePlantInGarden()
        advanceUntilIdle()

        val state = vm.uiState.value

        assertTrue(state.isSaved)
        assertEquals("Planta guardada en Mi Jardín", state.savedMessage)
    }

    @Test
    fun `al quitar una planta guardada actualiza estado a no guardada`() = runTest {
        val savedPlant = PlantDetail(
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

        val repo = FakePlantRepository(
            savedPlants = mutableListOf(savedPlant)
        )

        val vm = PlantDetailScreenViewModel(repo)

        vm.setPlantId(1)
        advanceUntilIdle()

        assertTrue(vm.uiState.value.isSaved)

        vm.togglePlantInGarden()
        advanceUntilIdle()

        val state = vm.uiState.value

        assertFalse(state.isSaved)
        assertEquals("Planta quitada de Mi Jardín", state.savedMessage)
    }

    @Test
    fun `clearSavedMessage limpia el mensaje`() = runTest {
        val repo = FakePlantRepository()
        val vm = PlantDetailScreenViewModel(repo)

        vm.setPlantId(1)
        advanceUntilIdle()

        vm.togglePlantInGarden()
        advanceUntilIdle()

        assertEquals(
            "Planta guardada en Mi Jardín",
            vm.uiState.value.savedMessage
        )

        vm.clearSavedMessage()

        assertNull(vm.uiState.value.savedMessage)
    }
}