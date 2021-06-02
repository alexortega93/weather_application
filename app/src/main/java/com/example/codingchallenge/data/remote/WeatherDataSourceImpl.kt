package com.example.codingchallenge.data.remote

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse
import com.example.codingchallenge.network.WeatherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataSourceImpl @Inject constructor(
    private val service: WeatherService
    ): BaseDataSource(), WeatherDataSource {
    override suspend fun getWeatherByCity(city: String): OperationResult<WeatherResponse> =
        getResult { service.getWeatherByCity(city) }

}