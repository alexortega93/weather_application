package com.example.codingchallenge.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.R
import com.example.codingchallenge.data.entities.*
import com.example.codingchallenge.data.repository.WeatherRepository
import com.example.codingchallenge.di.WeatherModule
import com.example.codingchallenge.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@UninstallModules(WeatherModule::class)
@RunWith(AndroidJUnit4::class)
class SearchByCityFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    lateinit var repository: WeatherRepository

    @Before
    fun init() { hiltRule.inject() }

    private val weather = WeatherResponse(
        city = City(
            "US", 0, "Atlanta", 0
        ),
        list = listOf(
            WeatherList(
                Main(75.5, 78.4),
                listOf(Weather("cloudy", "", 0, "Cloud"))
            ),
            WeatherList(
                Main(76.6, 79.5),
                listOf(Weather("cloudy", "", 0, "Cloud"))
            )
        )
    )

    @Test
    fun successWeatherConditions_navigateToListView() {
        // Given a valid location
        repository = Mockito.mock(WeatherRepository::class.java)


        runBlocking {
            Mockito.`when`(repository.getWeatherByCityName(Mockito.anyString()))
                .thenReturn(OperationResult.Success(weather))
        }

        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SearchByCityFragment>(Bundle()) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // When looking for a weather condition
        Espresso.onView(withId(R.id.et_search)).perform(ViewActions.replaceText("Atlanta"))
        Espresso.onView(withId(R.id.btn_search)).perform(ViewActions.click())

        // Then navigates to the list view
        Mockito.verify(navController).navigate(
            SearchByCityFragmentDirections.searchFragmentToWeatherListFragment("Atlanta")
        )
    }
}