package io.github.thang86.weathertest.db

import androidx.room.*
import io.github.thang86.weathertest.model.Weather
import io.github.thang86.weathertest.utils.Converters

/**
 *
 *    WeatherDao.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(list: Weather):Long

    @Query("SELECT * FROM weather_tables")
    suspend fun getWeather(): Weather

    //Updates movie's fields.
//    @Update
//    suspend fun update(weather: Weather)
}