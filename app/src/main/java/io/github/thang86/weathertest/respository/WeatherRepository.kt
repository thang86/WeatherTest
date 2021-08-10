package io.github.thang86.weathertest.repository

import androidx.lifecycle.LiveData
import io.github.thang86.weathertest.api.WeatherResponse
import io.github.thang86.weathertest.api.WeatherService
import io.github.thang86.weathertest.api.asModel
import io.github.thang86.weathertest.db.WeatherDb
import io.github.thang86.weathertest.model.Weather
import io.github.thang86.weathertest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *
 *    WeatherRepository.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
class WeatherRepository @Inject constructor(
    val service: WeatherService,
    val database: WeatherDb
) {
    suspend fun fetchWeather(): LiveData<Resource<List<Weather>>> {

        return object : NetworkBoundResource<List<Weather>, List<WeatherResponse>>() {
            override fun shouldFetch(data: List<Weather>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun loadFromDb(): List<Weather> {
                var mim = mutableListOf<Weather>();
                return withContext(Dispatchers.IO) {
                    mim.add(database.weatherDao.getWeather())
                    mim
                }
            }

            override suspend fun createCallAsync(): List<WeatherResponse> {
                return withContext(Dispatchers.IO) {
                    val response = service.fetchWeather().await()
                    response
                }
            }

            override fun processResponse(response: List<WeatherResponse>): List<Weather> {
                return response.asModel()
            }

            override suspend fun saveCallResults(items: List<Weather>) {

                withContext(Dispatchers.IO) {
                    database.weatherDao.insert(items[0])
                }
            }

            override fun onFetchFailed() {
            }


        }.build().asLiveData()
    }
}