package com.negm.app.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseManager {

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val database by lazy {
        FirebaseDatabase.getInstance()
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun currentUserId(): String? {
        return auth.currentUser?.uid
    }

    fun logout() {
        auth.signOut()
    }
}
