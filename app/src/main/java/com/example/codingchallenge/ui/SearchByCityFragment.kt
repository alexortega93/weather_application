package com.example.codingchallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.databinding.FragmentSearchWeatherByCityBinding
import com.example.codingchallenge.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchByCityFragment: Fragment() {

    private lateinit var binding: FragmentSearchWeatherByCityBinding
    private val viewModel by activityViewModels<WeatherViewModel>()
    private var weatherUpdateJob: Job? = null

    override fun onStart() {
        super.onStart()

        weatherUpdateJob = lifecycleScope.launch {
            viewModel.weatherState.collect { response ->
                when(response) {
                    is OperationResult.Loading -> {  }
                    is OperationResult.Error -> {

                        this@SearchByCityFragment.requireContext().toast(response.exception)
                    }
                    is OperationResult.Success -> {

                        findNavController().navigate(
                            SearchByCityFragmentDirections.searchFragmentToWeatherListFragment(binding.etSearch.text.toString())
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchWeatherByCityBinding.inflate(
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
        btnSearch.setOnClickListener { validateCity() }
    }

    private fun validateCity() {
        val city = binding.etSearch.text.toString()
        when {
            city.isEmpty() -> {
                this.requireContext().toast("Invalid City")
            }
            else -> {
                viewModel.getWeatherByCityName(city)
            }
        }
    }

    override fun onStop() {
        weatherUpdateJob?.cancel()
        super.onStop()
    }
}