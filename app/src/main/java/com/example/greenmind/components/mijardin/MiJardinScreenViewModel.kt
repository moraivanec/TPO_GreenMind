package com.example.greenmind.components.mijardin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.data.PlantRepository
import com.example.greenmind.domain.IPlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MiJardinScreenViewModel(
    private val plantRepository: IPlantRepository = PlantRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MiJardinScreenState())
    val uiState: StateFlow<MiJardinScreenState> = _uiState.asStateFlow()

    fun loadSavedPlants() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            try {
                val localPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedPlants = localPlants,
                        errorMessage = null
                    )
                }

                plantRepository.syncGardenFromRemote()

                val syncedPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedPlants = syncedPlants,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                val localPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedPlants = localPlants,
                        errorMessage = if (localPlants.isEmpty()) {
                            "No se pudieron cargar las plantas guardadas."
                        } else {
                            null
                        }
                    )
                }
            }
        }
    }

    fun removePlant(id: Int) {
        viewModelScope.launch {
            try {
                plantRepository.removePlantFromGarden(id)

                val updatedPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        savedPlants = updatedPlants,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "No se pudo quitar la planta."
                    )
                }
            }
        }
    }
}