package io.github.thang86.weathertest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *
 *    MyApp.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */
@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val applicationScope = CoroutineScope(Dispatchers.Default)
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
        }
    }
}