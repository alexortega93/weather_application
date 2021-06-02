package com.example.codingchallenge.data.repository

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse
import com.example.codingchallenge.fakeResponse

class FakeFepository: WeatherRepository {
    override suspend fun getWeatherByCityName(cityName: String): OperationResult<WeatherResponse> =
        OperationResult.Success(fakeResponse)
}