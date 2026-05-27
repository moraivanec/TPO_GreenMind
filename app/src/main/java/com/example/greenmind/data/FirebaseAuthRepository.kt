package com.example.greenmind.data

import com.example.greenmind.domain.IAuthRepository
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthRepository : IAuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun isUserLogged(): Boolean {
        return firebaseAuth.currentUser != null
    }
}