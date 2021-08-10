package com.ersiver.filmflop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.thang86.weathertest.api.WeatherService
import io.github.thang86.weathertest.db.WeatherDb
import io.github.thang86.weathertest.repository.WeatherRepository
import javax.inject.Singleton

/**
 *
 *    RepoModule.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */

@Module
@InstallIn(ApplicationComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepository(network: WeatherService, database: WeatherDb) =
        WeatherRepository(network, database)
}