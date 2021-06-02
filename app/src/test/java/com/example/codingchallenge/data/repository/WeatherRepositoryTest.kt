package com.example.codingchallenge.data.repository

import com.example.codingchallenge.MainCoroutineRule
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.remote.FakeRemoteDataSource
import com.example.codingchallenge.data.remote.WeatherDataSource
import com.example.codingchallenge.fakeResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private lateinit var weatherDataSource: WeatherDataSource
    private lateinit var repository: WeatherRepositoryImpl

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        weatherDataSource = FakeRemoteDataSource()
        repository = WeatherRepositoryImpl(weatherDataSource)
    }

    @Test
    fun getWeather_requestFromRemoteSource() = mainCoroutineRule.runBlockingTest {
        val response = repository.getWeatherByCityName("") as OperationResult.Success
        assertThat(response.data, IsEqual(fakeResponse))
    }
}