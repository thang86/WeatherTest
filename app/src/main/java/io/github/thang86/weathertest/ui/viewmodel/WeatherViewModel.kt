package io.github.thang86.weathertest.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.github.thang86.weathertest.model.Weather
import io.github.thang86.weathertest.repository.WeatherRepository
import io.github.thang86.weathertest.utils.Resource
import kotlinx.coroutines.launch

/**
 *
 *    WeatherViewModel.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */

class WeatherViewModel @ViewModelInject constructor(private val responsetory: WeatherRepository) :
    ViewModel() {
    private val _results = MediatorLiveData<Resource<List<Weather>>>()
    val results: LiveData<Resource<List<Weather>>> = _results
    private var movieSource: LiveData<Resource<List<Weather>>> = MutableLiveData()
    val input = MutableLiveData<String>()

    /**
     * Get the list of  weather via repository.
     * Submit the list to the adapter via BindingAdapters.
     */
    fun fetchWeather(date: String) {
        _results.removeSource(movieSource)
        viewModelScope.launch {
            movieSource = responsetory.fetchWeather(date)
            Log.e("THANGBCD", "--" + movieSource.value)
        }

        _results.addSource(movieSource) {
            _results.value = it
        }
    }
}