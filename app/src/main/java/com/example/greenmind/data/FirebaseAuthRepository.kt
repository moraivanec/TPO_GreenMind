package com.example.greenmind.data

import com.example.greenmind.domain.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IAuthRepository {

    override fun isUserLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }
}