package io.github.thang86.weathertest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 *
 *    Weather.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@Parcelize
@Entity(tableName = "weather_tables")
class Weather(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var autoIncrement: Int = 0,
    @ColumnInfo(name = "id") var id: String = "",
    @ColumnInfo(name = "weather_state_name") var weatherStateName: String = "",
    @ColumnInfo(name = "weather_state_abbr") var weatherStateAbbr: String = "",
    @ColumnInfo(name = "wind_direction_compass") var windDirectionCompass: String = "null",
    @ColumnInfo(name = "created") var created: String = "",
    @ColumnInfo(name = "applicable_date") var applicableDate: String = "2021/08/12",
    @ColumnInfo(name = "min_temp") var minTemp: Double = 0.0,
    @ColumnInfo(name = "max_temp") var maxTemp: Double = 0.0,
    @ColumnInfo(name = "the_temp") var theTemp: Double = 0.0,
    @ColumnInfo(name = "wind_speed") var windSpeed: Double = 0.0,
    @ColumnInfo(name = "wind_direction") var windDirection: Double = 0.0,
    @ColumnInfo(name = "air_pressure") var airPressure: Double = 0.0,
    @ColumnInfo(name = "humidity") var humidity: Int = 0,
    @ColumnInfo(name = "visibility") var visibility: Double = 0.0,
    @ColumnInfo(name = "predictability") var predictability: Int = 0
) : Parcelable
