package com.patricioveratapia.myweather.weather

import android.app.Application
import com.patricioveratapia.myweather.weather.di.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyWeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyWeatherApplication)
            modules(myModule)
        }
    }
}