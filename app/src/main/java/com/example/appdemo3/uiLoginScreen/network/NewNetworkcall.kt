package com.example.appdemo3.uiLoginScreen.network

import com.example.appdemo3.uiLoginScreen.MainActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewNetworkcall {

    val httploggingintercepter= HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private fun getretrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl( "https://qappwebapi-test.quipment.nl/").addConverterFactory(GsonConverterFactory.create())
            .client(okhttpclient()).build()
    }

    private fun okhttpclient() : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(getInterceptor()).build()
    }

    private fun getInterceptor() : Interceptor{
        return if (MainActivity.accessToken == ""){
            httploggingintercepter
        }else{
            AuthInterceptor()
        }
    }

    fun getapiservice()= getretrofit().create(ApiService::class.java)
}