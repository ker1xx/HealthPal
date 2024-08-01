package com.example.healthpal.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.healthpal.R
import com.example.healthpal.localdb.HealthPalDatabase
import com.example.healthpal.main.MainDisplay
import com.example.healthpal.utility.backgroundSetter
import com.example.healthpal.registration.RegistrationScreen
import com.example.healthpal.registration.RegistrationViewModel
import com.example.healthpal.registration.authProps.GoogleAuthUiClient
import com.example.healthpal.ui.theme.HealthPalTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = getSharedPreferences("SETTINGS", MODE_PRIVATE)
        setContent {
            HealthPalTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "splash_screen"
                ) {
                    composable("splash_screen") {
                        SplashScreen(navController, sharedPrefs.getBoolean("IS_LOGINNED",false))
                    }
                    composable("registration") {
                        val viewModel = viewModel<RegistrationViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()
                        val context = LocalContext.current

                        val database = HealthPalDatabase.getDbInstance(context)

                        val launcher =
                            rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch,
                                                db = database,
                                                context = context
                                            )
                                            viewModel.onSignIn(signInResult)
                                        }
                                    }
                                })

                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if (state.isSignInSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "Sign in successful",
                                    Toast.LENGTH_LONG
                                ).show()
                                val prefsEditor = sharedPrefs.edit()
                                prefsEditor.putBoolean("IS_LOGINNED", true)
                                prefsEditor.apply()
                                prefsEditor.commit()
                                navController.navigate("main")
                            }
                        }

                        RegistrationScreen(
                            state = state, onSigInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signInIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            }, navController = navController
                        )
                    }
                    composable("main") {
                        MainDisplay(
                            navController = navController
                        )
                    }
                    composable("workout") {

                    }
                    composable("running_tracker") {

                    }
                    composable("food_tracker") {

                    }
                    composable("water_tracker") {

                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController, isLoginned: Boolean = false) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        if (isLoginned) {
            navController.navigate("main")
            Timber.tag("info").i("go main")
        } else {
            navController.navigate("registration")
            Timber.tag("info").i("go reg")
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        backgroundSetter.BackgroundAnimation()
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val infiniteTransition = rememberInfiniteTransition(label = "")

            val angle by infiniteTransition.animateFloat(
                initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
                    tween(
                        1000, easing = LinearEasing
                    ),
                    RepeatMode.Restart,
                ), label = ""
            )
            val scale = infiniteTransition.animateFloat(
                initialValue = 0.7f, targetValue = 2f, animationSpec = infiniteRepeatable(
                    tween(
                        1000, easing = FastOutSlowInEasing
                    ), RepeatMode.Restart
                ), label = ""
            )
            Image(
                painter = painterResource(id = R.drawable.dumbbell),
                contentDescription = null,
                modifier = Modifier
                    .rotate(angle)
                    .scale(scale.value)
            )
//            GlobalScope.launch {
//                getWorkouts()
//            }
        }
    }
}