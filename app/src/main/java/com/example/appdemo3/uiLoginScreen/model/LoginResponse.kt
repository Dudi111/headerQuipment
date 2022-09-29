package com.example.appdemo3.uiLoginScreen.model

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)