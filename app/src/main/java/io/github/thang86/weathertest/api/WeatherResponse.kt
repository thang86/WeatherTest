package io.github.thang86.weathertest.api

import com.google.gson.annotations.SerializedName
import io.github.thang86.weathertest.model.Weather

/**
 *
 *    Weather.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
data class WeatherResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("weather_state_name") var weatherStateName: String? = null,
    @SerializedName("weather_state_abbr") var weatherStateAbbr: String? = null,
    @SerializedName("wind_direction_compass") var windDirectionCompass: String? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("applicable_date") var applicableDate: String? = null,
    @SerializedName("min_temp") var minTemp: Double? = null,
    @SerializedName("max_temp") var maxTemp: Double? = null,
    @SerializedName("the_temp") var theTemp: Double? = null,
    @SerializedName("wind_speed") var windSpeed: Double? = null,
    @SerializedName("wind_direction") var windDirection: Double? = null,
    @SerializedName("air_pressure") var airPressure: Double? = null,
    @SerializedName("humidity") var humidity: Int? = 0,
    @SerializedName("visibility") var visibility: Double? = 0.0,
    @SerializedName("predictability") var predictability: Int? = 0
)

fun List<WeatherResponse>.asModel(): List<Weather> {
    return map {
        Weather(
            id = it.id ?: "",
            weatherStateName = it.weatherStateName ?: "",
            weatherStateAbbr = it.weatherStateAbbr ?: "",
            windDirectionCompass = it.windDirectionCompass ?: "",
            created = it.created ?: "",
            applicableDate = it.applicableDate ?: "",
            minTemp = it.minTemp ?: 0.0,
            maxTemp = it.maxTemp ?: 0.0,
            theTemp = it.theTemp ?: 0.0,
            windSpeed = it.windSpeed ?: 0.0,
            windDirection = it.windDirection ?: 0.0,
            airPressure = it.airPressure ?: 0.0,
            humidity = it.humidity ?: 0,
            visibility = it.visibility ?: 0.0,
            predictability = it.predictability ?: 0


        )
    }
}