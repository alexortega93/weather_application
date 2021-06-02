package com.example.codingchallenge.data.entities


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherList(
    @Json(name = "main")
    val main: Main = Main(0.0, 0.0),
    @Json(name = "weather")
    val weather: List<Weather> = ArrayList(),
    val id: String = UUID.randomUUID().toString()
): Parcelable