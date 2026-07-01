package com.example.greenmind.components.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    // Canal interno para mandar eventos puntuales desde el viewModel hacia la pantalla
    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        // Si firebase ya tiene un usuario activo, se envía un evento para navegar
        if (firebaseAuth.currentUser != null) {
            viewModelScope.launch {
                _uiEvent.send("loginOK")
            }
        }
    }
}