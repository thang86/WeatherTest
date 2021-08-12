package io.github.thang86.weathertest.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 *    WeatherService.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
interface WeatherService {
    @GET("location/1252431/{query}/")
    suspend fun fetchWeather(@Path("query") query: String): List<WeatherResponse>
}