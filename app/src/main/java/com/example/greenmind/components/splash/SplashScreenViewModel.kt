package com.example.greenmind.components.splash

import androidx.lifecycle.ViewModel
import com.example.greenmind.domain.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {

    // Consulta al repo de autenticación si existe una sesión activa
    fun isUserLogged(): Boolean {
        return authRepository.isUserLogged()
    }
}