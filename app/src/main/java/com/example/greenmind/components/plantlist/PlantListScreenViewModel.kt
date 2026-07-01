package com.example.greenmind.components.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenmind.domain.IPlantRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantListScreenViewModel @Inject constructor(
    private val plantRepository: IPlantRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantListScreenState())
    val uiState: StateFlow<PlantListScreenState> = _uiState.asStateFlow()

    // Guarda la búsqueda/carga actual para poder cancelarla si se inicia otra
    private var fetchJob: Job? = null

    init {
        loadUserName()
        fetchPlants()
    }

    private fun loadUserName() {
        // Obtiene el nombre del usuario autenticado con Firebase
        val name = firebaseAuth.currentUser?.displayName ?: "Usuario"

        _uiState.update {
            it.copy(userName = name)
        }
    }

    fun searchChange(value: String) {
        // Actualiza el texto ingresado en el buscador
        _uiState.update {
            it.copy(searchQuery = value)
        }

        fetchPlants(delayMillis = 500)
    }

    fun fetchPlants(delayMillis: Long = 0) {
        // Cancela una carga anterior si todavia estaba en curso
        fetchJob?.cancel()

        fetchJob = viewModelScope.launch {

            // Se usa delay cuando la carga viene desde el buscador
            if (delayMillis > 0) {
                delay(delayMillis)
            }

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                // Solicita las plantas al repo según el texto de búsqueda
                val plants = plantRepository.fetchPlants(
                    _uiState.value.searchQuery
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        plantList = plants,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {
                android.util.Log.e(
                    "PlantListScreenViewModel",
                    "Error fetching plants",
                    e
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No se pudieron cargar las plantas."
                    )
                }
            }
        }
    }
}