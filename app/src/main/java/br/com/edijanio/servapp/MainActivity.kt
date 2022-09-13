package br.com.edijanio.servapp

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.edijanio.servapp.repository.ChamadosRepository
import br.com.edijanio.servapp.ui.theme.ServAppTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
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
                    Main(auth)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ChamadosRepository().getWeather()
    }
}

@Composable
fun Main(auth: FirebaseAuth) {
    val navController = rememberNavController()
    var isLogged = false
    if (auth.currentUser != null) {
        isLogged = true
    }
    var destination = "login"
    if (isLogged) destination = "main"

    NavHost(navController = navController, startDestination = destination) {
        composable("login") { Login(auth, navController) }
        composable("register") { Register(auth, navController) }
        composable("main") { MainScreen(auth, navController) }
    }
}

@Composable
fun Register(auth: FirebaseAuth, navController: NavHostController) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column {

        Row (verticalAlignment = Alignment.CenterVertically) {
            Text("Nome", Modifier.padding(end = 10.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = {Text("José")}
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically){
            Text("E-mail", Modifier.padding(end = 10.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {Text("jose@gmail.com")}
            )
        }

        Row (verticalAlignment = Alignment.CenterVertically){
            Text("Senha", Modifier.padding(end = 10.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {Text("******")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Button(onClick = {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    navController.navigate("main")
                }
            }

        }) {
            Icon(Icons.Filled.Done, contentDescription = "")
            Text("Registrar")
        }
    }
}

@Composable
fun Login(auth: FirebaseAuth, navController: NavHostController) {
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
            modifier = Modifier.padding(vertical = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        ClickableText(text = AnnotatedString("Esqueci a senha!"), onClick = {
            navController.navigate("register")
        })

        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    navController.navigate(route = "main")
                } else {
                    Toast.makeText(MainActivity(), "Email ou Senha inválido", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        {
            Icon(Icons.Filled.Send, contentDescription = "button")
        }


    }
}

@Composable
fun MainScreen(auth: FirebaseAuth, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        val authUser = auth.currentUser

        if (authUser != null) {
            Text(
                text = if(authUser.displayName != "" ) "${authUser.displayName}" else "Usuario",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text("Email é verificado: ${authUser.isEmailVerified}")
        }

        Button(onClick = {
            auth.signOut()
            navController.navigate("login")
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.TwoTone.ArrowBack, contentDescription = "yeah")
            Text(text = "Logout")
        }

        Button(onClick = {

        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.TwoTone.Check, contentDescription = "yeah")
            Text(text = "Verificar Email")
        }

        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.TwoTone.Add, contentDescription = "yeah")
            Text(text = "Add User")
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