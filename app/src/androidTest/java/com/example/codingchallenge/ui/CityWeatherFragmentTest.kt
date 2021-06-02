package com.example.codingchallenge.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.R
import com.example.codingchallenge.RecyclerViewMatcher
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
import org.mockito.Mockito

@MediumTest
@HiltAndroidTest
@UninstallModules(WeatherModule::class)
@RunWith(AndroidJUnit4::class)
class CityWeatherFragmentTest {

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
    fun loadData() {
        repository = Mockito.mock(WeatherRepository::class.java)


        runBlocking {
            Mockito.`when`(repository.getWeatherByCityName(Mockito.anyString()))
                .thenReturn(OperationResult.Success(weather))
        }

        ActivityScenario.launch(MainActivity::class.java)
        // When looking for a weather condition
        onView(ViewMatchers.withId(R.id.et_search)).perform(ViewActions.replaceText("Atlanta"))
        onView(ViewMatchers.withId(R.id.btn_search)).perform(ViewActions.click())

        onView(RecyclerViewMatcher(R.id.rv_weather)
            .atPositionOnView(0, R.id.tv_weather_main))
            .check(ViewAssertions.matches(withText("Sunny")))
        onView(RecyclerViewMatcher(R.id.rv_weather)
                .atPositionOnView(0, R.id.tv_weather_description))
            .check(ViewAssertions.matches(withText("(sun)")))
        onView(RecyclerViewMatcher(R.id.rv_weather)
            .atPositionOnView(0, R.id.tv_weather_temp))
            .check(ViewAssertions.matches(withText("Temp: 78.4")))

        onView(RecyclerViewMatcher(R.id.rv_weather)
            .atPositionOnView(1, R.id.tv_weather_main))
            .check(ViewAssertions.matches(withText("Cloud")))
        onView(RecyclerViewMatcher(R.id.rv_weather)
            .atPositionOnView(1, R.id.tv_weather_description))
            .check(ViewAssertions.matches(withText("(cloudy)")))
        onView(RecyclerViewMatcher(R.id.rv_weather)
            .atPositionOnView(1, R.id.tv_weather_temp))
            .check(ViewAssertions.matches(withText("Temp: 79.5")))
    }
}