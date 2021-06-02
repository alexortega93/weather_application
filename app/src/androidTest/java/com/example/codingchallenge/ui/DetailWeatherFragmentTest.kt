package com.example.codingchallenge.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.R
import com.example.codingchallenge.WeatherModule
import com.example.codingchallenge.data.entities.*
import com.example.codingchallenge.data.repository.WeatherRepository
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@UninstallModules(WeatherModule::class)
@RunWith(AndroidJUnit4::class)
class DetailWeatherFragmentTest {

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
                listOf(Weather("sun", "", 0, "Sunny"))
            ),
            WeatherList(
                Main(76.6, 79.5),
                listOf(Weather("cloudy", "", 0, "Cloud"))
            )
        )
    )

    @Test
    fun showDetailedWeatherInformation() {

        repository = Mockito.mock(WeatherRepository::class.java)


        runBlocking {
            Mockito.`when`(repository.getWeatherByCityName(anyString()))
                .thenReturn(OperationResult.Success(weather))
        }

        ActivityScenario.launch(MainActivity::class.java)
        // When looking for a weather condition
        onView(withId(R.id.et_search)).perform(replaceText("Atlanta"))
        onView(withId(R.id.btn_search)).perform(click())

        onView(withId(R.id.rv_weather))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<WeatherAdapter.WeatherViewHolder>(
                    1, click()
                )
            )

        onView(withId(R.id.tv_weather_main)).check(matches(withText("Cloud")))
        onView(withId(R.id.tv_weather_description)).check(matches(withText("cloudy")))
        onView(withId(R.id.tv_weather_temp)).check(matches(withText("79.5")))
        onView(withId(R.id.tv_weather_feels_like)).check(matches(withText("Feels Like: 76.6")))
    }
}