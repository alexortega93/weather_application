package com.example.codingchallenge.data.entities


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "temp")
    val temp: Double
): Parcelable