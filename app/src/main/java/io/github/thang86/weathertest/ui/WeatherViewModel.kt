package io.github.thang86.weathertest.ui

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


    /**
     * Get the list of searched movies via repository.
     * Submit the list to the adapter via BindingAdapters.
     */
    fun search() {
        _results.removeSource(movieSource)
        viewModelScope.launch {
            movieSource = responsetory.fetchWeather()
            Log.e("THANGBCD", "--" + movieSource.value)
        }

        _results.addSource(movieSource) {
            _results.value = it
        }
    }
}