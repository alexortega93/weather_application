package com.example.codingchallenge.data.entities


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherResponse(
    @Json(name = "city")
    val city: City? = null,
    @Json(name = "cod")
    val cod: String = "",
    @Json(name = "list")
    val list: List<WeatherList> = ArrayList(),
    @Json(name = "message")
    val message: Int = 0
): Parcelable