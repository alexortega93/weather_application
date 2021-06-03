package com.example.codingchallenge.network

import com.example.codingchallenge.BuildConfig
import com.example.codingchallenge.data.entities.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/forecast")
    suspend fun getWeatherByCity(
        @Query("q")
        city: String,
        @Query("appid")
        apiKey: String = BuildConfig.API_KEY,
        @Query("units")
        units: String = "imperial"
    ): Response<WeatherResponse>

}