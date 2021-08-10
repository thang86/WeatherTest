package io.github.thang86.weathertest.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.thang86.weathertest.db.WeatherDb
import javax.inject.Singleton

/**
 *
 *    DatabaseModule.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providerDatabase(app: Application): WeatherDb {
        return Room.databaseBuilder(app, WeatherDb::class.java, "weather_db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDb) = db.weatherDao

}