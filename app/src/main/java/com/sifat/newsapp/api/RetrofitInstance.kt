package com.sifat.newsapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private const val BASE_URL = "https://newsapi.org/" // Ensure the correct base URL

        private val retrofit by lazy {
            // Create an HTTP logging interceptor
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Build OkHttpClient with the interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            // Build Retrofit instance
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
                .client(client) // Attach OkHttp client
                .build()
        }
        val api by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}