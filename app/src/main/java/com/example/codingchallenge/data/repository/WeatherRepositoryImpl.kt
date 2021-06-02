package com.example.codingchallenge.data.repository

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse
import com.example.codingchallenge.data.remote.WeatherDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherDataSource
): WeatherRepository {

    override suspend fun getWeatherByCityName(cityName: String): OperationResult<WeatherResponse> =
        remoteDataSource.getWeatherByCity(cityName)
}