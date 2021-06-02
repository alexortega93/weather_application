package com.example.codingchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.codingchallenge.databinding.FragmentWeatherDetailBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailWeatherFragment: Fragment() {

    private lateinit var binding: FragmentWeatherDetailBinding
    private val viewModel by activityViewModels<WeatherViewModel>()
    private var weatherUpdateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setDetailedItem(-1)
                isEnabled = false
                activity?.onBackPressed()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        weatherUpdateJob = lifecycleScope.launch {
            viewModel.itemSelected.collect { item ->
                if (item.main.temp != 0.0) {
                    binding.apply {
                        tvWeatherMain.text = item.weather[0].main
                        tvWeatherDescription.text = item.weather[0].description
                        tvWeatherTemp.text = item.main.temp.toString()
                        tvWeatherFeelsLike.text = "Feels Like: ${item.main.feelsLike}"
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onStop() {
        weatherUpdateJob?.cancel()
        super.onStop()
    }
}