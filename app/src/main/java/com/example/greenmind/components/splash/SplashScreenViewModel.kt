package com.example.greenmind.components.splash

import androidx.lifecycle.ViewModel
import com.example.greenmind.domain.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {

    fun isUserLogged(): Boolean {
        return authRepository.isUserLogged()
    }
}