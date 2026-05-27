package com.example.greenmind.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://perenual.com/api/v2/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val plantAPI: IPlantAPI by lazy {
        retrofit.create(IPlantAPI::class.java)
    }
}