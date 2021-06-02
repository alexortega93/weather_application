package com.example.codingchallenge.data.repository

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherByCityName(cityName: String): OperationResult<WeatherResponse>
}