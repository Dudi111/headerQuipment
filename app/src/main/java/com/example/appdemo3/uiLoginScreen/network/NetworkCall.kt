package com.example.appdemo3.uiLoginScreen.network

import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object NetworkCall {

    private lateinit var interceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private var retrofit: Retrofit? = null
    var token:String=""

    val client: Retrofit
        get() {
            interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectionSpecs(
                    Arrays.asList(
                    ConnectionSpec.MODERN_TLS,
                    ConnectionSpec.COMPATIBLE_TLS))
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .cache(null)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://qappwebapi-test.quipment.nl/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!

        }

    fun getApiService()= client.create(ApiService::class.java)
}