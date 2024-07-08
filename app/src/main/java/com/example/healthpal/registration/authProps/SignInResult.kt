package com.example.healthpal.registration.authProps

import android.graphics.Picture

data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
)
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)

