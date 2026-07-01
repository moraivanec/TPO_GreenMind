package com.example.greenmind.components.buscar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.domain.IPlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuscarScreenViewModel @Inject constructor(
    private val plantRepository: IPlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BuscarScreenState())
    val uiState: StateFlow<BuscarScreenState> = _uiState.asStateFlow()

    // Guarda la búsqeuda actual para poder cancelarla si el usuario sigue escribiendo
    private var searchJob: Job? = null

    fun searchChange(value: String) {
        _uiState.update {
            it.copy(searchQuery = value)
        }

        // Si el campo queda vacío, se cancela la busqueda y se limpia la lista
        if (value.isBlank()) {
            searchJob?.cancel()
            _uiState.update {
                it.copy(
                    plantList = emptyList(),
                    isLoading = false,
                    errorMessage = null
                )
            }
            return
        }

        searchPlants()
    }

    private fun searchPlants() {
        // Cancela la búsqueda anterior para evitar consultas innecesarias
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(500)

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val plants = plantRepository.fetchPlants(_uiState.value.searchQuery)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        plantList = plants,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudieron buscar plantas."
                    )
                }
            }
        }
    }
}