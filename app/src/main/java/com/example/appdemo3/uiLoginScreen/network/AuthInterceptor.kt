package com.example.appdemo3.uiLoginScreen.network

import com.example.appdemo3.uiLoginScreen.MainActivity
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest= chain.request().newBuilder()
        newRequest.addHeader("Authorization", "Bearer ${MainActivity.accessToken}")
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
        return chain.proceed(newRequest.build())
    }

}