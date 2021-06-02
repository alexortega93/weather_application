package com.example.codingchallenge.data.remote

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse

interface WeatherDataSource {
    suspend fun getWeatherByCity(city: String): OperationResult<WeatherResponse>
}