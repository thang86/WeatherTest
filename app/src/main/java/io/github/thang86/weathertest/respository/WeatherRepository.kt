package io.github.thang86.weathertest.repository

import android.util.Log
import android.widget.Toast
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
    suspend fun fetchWeather(date: String): LiveData<Resource<List<Weather>>> {

        return object : NetworkBoundResource<List<Weather>, List<WeatherResponse>>() {
            override fun shouldFetch(data: List<Weather>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): List<Weather> {
                var mim = mutableListOf<Weather>();
                return withContext(Dispatchers.IO) {
                    var oldList = database.weatherDao.getWeather()
                     if (oldList.isNotEmpty()) {
                        mim.add(oldList[oldList.size - 1])

                    } else {
                        mim.add(Weather())
                    }
//                    Log.e("THANTX",""+mim.size)
                    mim
                }
            }

            override suspend fun createCallAsync(): List<WeatherResponse> {
                return withContext(Dispatchers.Main) {
                    try {
                        Log.e("ThangTX2899", "Sss" + date)
                        val response = service.fetchWeather(date)
                        response
                    } catch (e: Exception) {
                        e.printStackTrace()
                        mutableListOf()
                    }

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
                Log.e("ERRRRRR", "RRR")
            }


        }.build().asLiveData()
    }
}