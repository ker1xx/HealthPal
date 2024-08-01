package com.example.healthpal.registration

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.healthpal.R
import com.example.healthpal.utility.backgroundSetter
import com.example.healthpal.registration.authProps.SignInState


@Composable
fun RegistrationScreen(
    state: SignInState,
    onSigInClick: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context, error, Toast.LENGTH_LONG
            ).show()
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        backgroundSetter.BackgroundAnimation()
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .background(Color.Transparent)
        ) {
            Button(
                onClick = onSigInClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    ),
                border = BorderStroke(
                    2.dp, MaterialTheme.colorScheme.outline
                ),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.inverseSurface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_ic_96),
                    contentDescription = null,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier
                        .padding(end = 10.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = "Sign In with Google",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
        }
    }
}