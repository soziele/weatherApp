package com.example.pogodynka.api

import com.example.pogodynka.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") apiKey: String, @Query("units") units: String = "metric"): Response<Weather>

}