package com.example.pogodynka.repository

import com.example.pogodynka.api.RetrofitInstance
import com.example.pogodynka.model.Weather
import retrofit2.Response

class Repository {
    suspend fun getWeather(city: String, apiKey: String): Response<Weather> {
        return RetrofitInstance.api.getWeather(city, apiKey)
    }
}