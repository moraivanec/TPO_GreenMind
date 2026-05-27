package com.example.greenmind.components.plantdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.data.PlantRepository
import com.example.greenmind.domain.IPlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantDetailScreenViewModel(
    private val plantRepository: IPlantRepository = PlantRepository()
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
                    errorMessage = null
                )
            }

            try {
                val plant = plantRepository.fetchPlantDetail(id)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        plant = plant,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudo cargar el detalle de la planta."
                    )
                }
            }
        }
    }
}