package com.example.codingchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.OperationResult
import com.example.codingchallenge.data.entities.WeatherList
import com.example.codingchallenge.data.entities.WeatherResponse
import com.example.codingchallenge.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
): ViewModel() {
    private val _weatherState = MutableStateFlow<OperationResult<WeatherResponse>>(OperationResult.Starting)

    val weatherState: StateFlow<OperationResult<WeatherResponse>> get() = _weatherState

    private val _weatherSituation = MutableStateFlow(listOf<WeatherList>())

    val weatherSituation: StateFlow<List<WeatherList>> get() = _weatherSituation

    private val _itemSelected = MutableStateFlow(WeatherList())

    val itemSelected: StateFlow<WeatherList> get() = _itemSelected

    fun getWeatherByCityName(cityName: String) {
        _weatherState.value = OperationResult.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                repository.getWeatherByCityName(cityName)
            }
            _weatherState.value = response
            if(response is OperationResult.Success) {
                _weatherSituation.value = response.data.list
            }
        }
    }

    fun setWeatherToInitialState() {
        _weatherState.value = OperationResult.Starting
        _weatherSituation.value = ArrayList()
    }

    fun setDetailedItem(position: Int) {
        _itemSelected.value = if(position >= 0) {
            _weatherSituation.value[position]
        } else {
            WeatherList()
        }
    }
}