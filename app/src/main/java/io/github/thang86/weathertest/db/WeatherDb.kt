package io.github.thang86.weathertest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.thang86.weathertest.model.Weather

/**
 *
 *    WeatherDb.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@Database(
    entities = [Weather::class], version = 3, exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}