package com.example.codingchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.codingchallenge.databinding.FragmentWeatherListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CityWeatherFragment: Fragment() {

    private lateinit var binding: FragmentWeatherListBinding
    private lateinit var adapter: WeatherAdapter
    private val viewModel by activityViewModels<WeatherViewModel>()
    private var weatherUpdateJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setWeatherToInitialState()
                isEnabled = false
                activity?.onBackPressed()
            }
        })
    }

    override fun onStart() {
        super.onStart()

        weatherUpdateJob = lifecycleScope.launch {
            viewModel.weatherSituation.collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() = with(binding) {
        adapter = WeatherAdapter() {
            viewModel.setDetailedItem(it)
            findNavController()
                .navigate(
                    CityWeatherFragmentDirections
                        .weatherListFragmentToWeatherDetailFragment()
                )
        }
        rvWeather.adapter = adapter
    }

    override fun onStop() {

        weatherUpdateJob?.cancel()
        super.onStop()
    }
}