package br.com.edijanio.servapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.edijanio.servapp.repository.FirebaseRepository
import br.com.edijanio.servapp.ui.theme.ServAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            ServAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.padding(20.dp)) {
                    Login(auth)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Log.d("firebase_auth", "$currentUser")
        if (currentUser != null) {
            Toast.makeText(this, "user already connected", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Login(auth: FirebaseAuth) {
    Column() {
        Text(text = "Email: ", Modifier.padding(vertical = 10.dp))
        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Insira seu E-mail") },
            modifier = Modifier.padding(vertical = 10.dp)
        )

        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Insira sua senha") },
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Button(onClick = {
            val fire = Firebase
            val repository = FirebaseRepository(fire)
            repository.loginUser(email, password)
           // loginWithEmailAndPassword(email, password, auth)
        })
        {
            Icon(Icons.Filled.Add, contentDescription = "button")
        }
    }
}

fun loginWithEmailAndPassword(email: String, password: String, auth: FirebaseAuth) {
    if (email.isNotEmpty()) {
        if (password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("firebase_auth", "signInWithEmail:success - ${auth.currentUser}")
                } else {
                    Log.d("firebase_auth", "signInWithEmail:fail - ${auth.currentUser}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ServAppTheme {
        //Login()
    }
}