package com.example.codingchallenge.data.remote

import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherResponse
import com.example.codingchallenge.fakeResponse

class FakeRemoteDataSource: WeatherDataSource {
    override suspend fun getWeatherByCity(city: String): OperationResult<WeatherResponse> =
        OperationResult.Success(fakeResponse)
}