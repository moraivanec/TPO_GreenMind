package com.example.greenmind.components.splash

import androidx.lifecycle.ViewModel
import com.example.greenmind.data.FirebaseAuthRepository
import com.example.greenmind.domain.IAuthRepository


class SplashScreenViewModel(
    private val authRepository: IAuthRepository = FirebaseAuthRepository()
) : ViewModel() {

    fun isUserLogged(): Boolean {
        return authRepository.isUserLogged()
    }

}