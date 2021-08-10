package io.github.thang86.weathertest.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 *
 *    WeatherService.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
interface WeatherService {
    @GET("location/1252431/2021/07/31/")
    fun fetchWeather(): Deferred<List<WeatherResponse>>
}