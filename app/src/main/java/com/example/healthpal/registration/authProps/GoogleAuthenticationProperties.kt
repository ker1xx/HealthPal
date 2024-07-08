package com.example.healthpal.registration.authProps

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.healthpal.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleAuthenticationProperties(private var context: Context) {
    lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient : GoogleSignInClient

    fun getInstance()
    {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

            // Your server's client ID, not your Android client ID.
            .requestIdToken("47514929567-klio2k2pca3drse4nbipuc2qbjm344m4.apps.googleusercontent.com")
            .requestEmail()
            // Only show accounts previously used to sign in.
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)

        auth = Firebase.auth

        val currentUser = auth.currentUser
    }


    fun signInGoogle(launcher : ActivityResultLauncher<Intent>){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

}