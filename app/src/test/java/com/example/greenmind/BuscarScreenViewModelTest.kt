package com.example.greenmind

import com.example.greenmind.components.buscar.BuscarScreenViewModel
import com.example.greenmind.data.Plant
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BuscarScreenViewModelTest {

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
    fun `al buscar una planta actualiza el listado`() = runTest {
        val fakePlants = listOf(
            Plant(
                id = 1,
                commonName = "Rose",
                scientificName = "Rosa",
                careLevel = "Easy",
                imageUrl = ""
            ),
            Plant(
                id = 2,
                commonName = "Cactus",
                scientificName = "Cactaceae",
                careLevel = "Easy",
                imageUrl = ""
            )
        )

        val repo = FakePlantRepository(plantList = fakePlants)
        val vm = BuscarScreenViewModel(repo)

        vm.searchChange("Rose")

        advanceTimeBy(500)
        advanceUntilIdle()

        val state = vm.uiState.value

        assertEquals("Rose", state.searchQuery)
        assertEquals(1, state.plantList.size)
        assertEquals("Rose", state.plantList[0].commonName)
        assertFalse(state.isLoading)
    }

    @Test
    fun `si la busqueda esta vacia limpia el listado`() = runTest {
        val fakePlants = listOf(
            Plant(
                id = 1,
                commonName = "Rose",
                scientificName = "Rosa",
                careLevel = "Easy",
                imageUrl = ""
            )
        )

        val repo = FakePlantRepository(plantList = fakePlants)
        val vm = BuscarScreenViewModel(repo)

        vm.searchChange("")

        advanceUntilIdle()

        val state = vm.uiState.value

        assertEquals("", state.searchQuery)
        assertEquals(0, state.plantList.size)
        assertFalse(state.isLoading)
    }
}