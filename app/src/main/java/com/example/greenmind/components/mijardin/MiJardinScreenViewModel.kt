package com.example.greenmind.components.mijardin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.domain.IPlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MiJardinScreenViewModel @Inject constructor(
    private val plantRepository: IPlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MiJardinScreenState())
    val uiState: StateFlow<MiJardinScreenState> = _uiState.asStateFlow()

    fun loadSavedPlants() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            try {
                // Primero se cargan las plantas guardadas localmente desde Room
                val localPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedPlants = localPlants,
                        errorMessage = null
                    )
                }

                // Después sincroniza el jardín con Firestore
                plantRepository.syncGardenFromRemote()

                // Sincroniza y se vuelve a leer Room para mostrar datos actualizados
                val syncedPlants = plantRepository.getSavedPlants()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        savedPlants = syncedPlants,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                // Si falla la sincronización remota, se intenta mantener visible la información local
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
                // El repo se ocupa de eliminar la planta localmente y en remoto
                plantRepository.removePlantFromGarden(id)

                // Consulta de nuevo la lista para actualizar la pantalla
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