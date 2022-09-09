package br.com.edijanio.servapp.repository

import android.util.Log
import br.com.edijanio.servapp.models.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseRepository(
    firebase: Firebase
) {
    val auth = firebase.auth

    fun loginUser(email: String, password: String): User {
        var user = User()
        Log.d("firebase", "1: ${user.name}")
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            user = User(
                name = it.user,
                userInfo = it.additionalUserInfo,
                credential = it.credential
            )
        }
        Log.d("firebase", "2: ${user.name}")

        return user
    }
}