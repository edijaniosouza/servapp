package br.com.edijanio.servapp.models

import com.google.firebase.auth.AdditionalUserInfo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

data class User(
    val name: FirebaseUser? = null,
    val userInfo: AdditionalUserInfo? = null,
    val credential: AuthCredential? = null
)
