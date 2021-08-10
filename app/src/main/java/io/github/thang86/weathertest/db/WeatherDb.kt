package io.github.thang86.weathertest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Provides
import io.github.thang86.weathertest.model.Weather
import io.github.thang86.weathertest.utils.Converters
import javax.inject.Singleton

/**
 *
 *    WeatherDb.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@Database(
    entities = [Weather::class], version = 1, exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {

    abstract val weatherDao: WeatherDao
}