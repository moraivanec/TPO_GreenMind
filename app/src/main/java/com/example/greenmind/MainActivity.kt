package com.example.greenmind

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.greenmind.components.NavigationStack
import com.example.greenmind.components.Screen
import com.example.greenmind.ui.theme.GreenMindTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var navController: NavHostController
    private lateinit var googleSignInClient: GoogleSignInClient

    // Launcher encargado de abrir el flujo de inicio de sesión con Google
    // Y recibir el resultado cuando el usuario termina el proceso
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                if (idToken != null) {
                    // Convierte el token de Google en una credencial válida para Firebase
                    val credential = GoogleAuthProvider.getCredential(idToken, null)

                    // Inicia sesión en Firebase usando la credencial de Google
                    firebaseAuth
                        .signInWithCredential(credential)
                        .addOnCompleteListener { authResult ->

                            if (authResult.isSuccessful) {
                                navController.navigate(Screen.PlantList.route) {
                                    popUpTo(Screen.Splash.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                } else {
                    Log.e(
                        "GREENMIND_LOGIN",
                        "ID Token null. Revisar configuración de GoogleSignInOptions."
                    )
                }

            } catch (e: ApiException) {
                Log.e(
                    "GREENMIND_LOGIN",
                    "Error de autenticación",
                    e
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configura Google Sign-in solicitando el ID tokem necesario para Firebase Auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            navController = rememberNavController()

            GreenMindTheme {
                NavigationStack(
                    navController = navController,

                    onGoogleLoginClick = {
                        launcher.launch(googleSignInClient.signInIntent)
                    },

                    onLogoutClick = {
                        FirebaseAuth.getInstance().signOut()

                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.PlantList.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}