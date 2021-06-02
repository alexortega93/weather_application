package com.example.codingchallenge

import com.example.codingchallenge.data.entities.*

val fakeResponse: WeatherResponse =
        WeatherResponse(
            city = City(
                "US",
                0,
                "Atlanta",
                0
            ),
            list = arrayListOf(
                WeatherList(
                    Main(100.0, 100.0),
                    arrayListOf(
                        Weather("Broken clouds", "", 0, "Cloudy")
                    )
                ),
                WeatherList(
                    Main(90.0, 90.0),
                    arrayListOf(
                        Weather("Broken clouds", "", 0, "Cloudy")
                    )
                ),
                WeatherList(
                    Main(80.0, 80.0),
                    arrayListOf(
                        Weather("Broken clouds", "", 0, "Cloudy")
                    )
                )
            )
        )