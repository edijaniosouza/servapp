package br.com.edijanio.servapp.repository

import android.util.Log
import br.com.edijanio.servapp.models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseRepository(
    firebase: Firebase
) {
    val auth = firebase.auth

    fun loginUser(email: String, password: String){
        val t = mutableListOf<User>()

        auth.signInWithEmailAndPassword("edijanio.souza@hotmail.com", "123456").addOnSuccessListener {
            t.add(User(
                name = it.user,
                userInfo = it.additionalUserInfo,
                credential = it.credential
            ))
        }
        Log.d("firebase","$t")
    }
}