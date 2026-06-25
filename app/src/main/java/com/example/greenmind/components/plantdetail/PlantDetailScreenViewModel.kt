package com.example.greenmind.components.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.domain.IPlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlantDetailScreenViewModel @Inject constructor(
    private val plantRepository: IPlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantDetailScreenState())
    val uiState: StateFlow<PlantDetailScreenState> = _uiState.asStateFlow()

    private var currentPlantId: Int = 0
    private var fetchJob: Job? = null

    fun setPlantId(id: Int) {
        if (currentPlantId == id) {
            return
        }

        currentPlantId = id
        fetchPlantDetail(id)
    }

    private fun fetchPlantDetail(id: Int) {
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    savedMessage = null
                )
            }

            try {
                val plant = plantRepository.fetchPlantDetail(id)
                val isSaved = plantRepository.isPlantSaved(id)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        plant = plant,
                        errorMessage = null,
                        isSaved = isSaved
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el detalle de la planta"
                    )
                }
            }
        }
    }

    fun togglePlantInGarden() {
        viewModelScope.launch {
            val plant = _uiState.value.plant ?: return@launch

            try {
                if (_uiState.value.isSaved) {
                    plantRepository.removePlantFromGarden(plant.id)

                    _uiState.update {
                        it.copy(
                            isSaved = false,
                            savedMessage = "Planta quitada de Mi Jardín"
                        )
                    }
                } else {
                    plantRepository.savePlantInGarden(plant)

                    _uiState.update {
                        it.copy(
                            isSaved = true,
                            savedMessage = "Planta guardada en Mi Jardín"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        savedMessage = "No se pudo actualizar Mi Jardín"
                    )
                }
            }
        }
    }

    fun clearSavedMessage() {
        _uiState.update {
            it.copy(savedMessage = null)
        }
    }
}