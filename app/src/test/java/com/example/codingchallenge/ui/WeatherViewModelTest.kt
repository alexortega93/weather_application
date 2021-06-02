package com.example.codingchallenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.codingchallenge.MainCoroutineRule
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.repository.FakeFepository
import com.example.codingchallenge.data.repository.WeatherRepository
import com.example.codingchallenge.fakeResponse
import com.example.codingchallenge.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var viewModel: WeatherViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var repo: WeatherRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupWeatherViewModel() {
        // We initialise the repository with no tasks
        repo = FakeFepository()

        viewModel = WeatherViewModel(repo)
    }

    @Test
    fun loadWeatherConditions_getSuccessResponse() = mainCoroutineRule.runBlockingTest {
        // Make the repository return a Success response

        viewModel.getWeatherByCityName("Atlanta")
        viewModel.weatherSituation.asLiveData().getOrAwaitValue()
        MatcherAssert.assertThat(
            viewModel.weatherState.asLiveData().getOrAwaitValue(),
            CoreMatchers.`is`(OperationResult.Success(fakeResponse))
        )
    }

    @Test
    fun loadWeatherConditions_getDetailedItem() = mainCoroutineRule.runBlockingTest {
        // Make the repository return a Success response

        viewModel.getWeatherByCityName("Atlanta")
        viewModel.weatherSituation.asLiveData().getOrAwaitValue()
        viewModel.setDetailedItem(1)
        val item = viewModel.itemSelected.asLiveData().getOrAwaitValue()
        MatcherAssert.assertThat(
            item,
            CoreMatchers.`is`(fakeResponse.list[1])
        )
    }

    @Test
    fun loadWeatherConditions_getInitialState() = mainCoroutineRule.runBlockingTest {
        // Make the repository return a Success response

        val item = viewModel.weatherState.asLiveData().getOrAwaitValue()
        MatcherAssert.assertThat(
            item,
            CoreMatchers.`is`(OperationResult.Starting)
        )
    }
}