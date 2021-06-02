package com.example.codingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.codingchallenge.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.nav_host).addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.search_fragment) {
                title = "Weather"
            } else if (destination.id == R.id.weather_list_fragment) {
                title = arguments?.getString("cityName","City Name")
            }
        }
    }
}