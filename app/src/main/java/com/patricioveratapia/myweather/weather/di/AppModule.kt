package com.patricioveratapia.myweather.weather.di

import android.content.Context
import androidx.room.Room
import com.patricioveratapia.myweather.weather.data.CityRepositoryImpl
import com.patricioveratapia.myweather.weather.data.WeatherRepositoryImpl
import com.patricioveratapia.myweather.weather.data.database.WeatherDao
import com.patricioveratapia.myweather.weather.data.database.WeatherDatabase
import com.patricioveratapia.myweather.weather.data.mapper.WeatherDatabaseMapper
import com.patricioveratapia.myweather.weather.data.mapper.WeatherMapper
import com.patricioveratapia.myweather.weather.data.network.RetrofitService
import com.patricioveratapia.myweather.weather.ui.CityViewModel
import com.patricioveratapia.myweather.weather.ui.HomeViewModel
import com.patricioveratapia.myweather.weather.ui.interfaces.CityRepository
import com.patricioveratapia.myweather.weather.ui.interfaces.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val myModule: Module = module {

    viewModel { (city: String) ->
        HomeViewModel(
            get(),
            city
        )
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            get(),
            get(),
            get(),
            get()
        )
    }

    single { RetrofitService() }

    single { WeatherMapper() }

    single { WeatherDatabaseMapper() }

    single { provideWeatherDao(androidContext()) }

    viewModel { CityViewModel(get()) }

    single<CityRepository> {
        CityRepositoryImpl()
    }

}

private fun provideWeatherDao(context: Context): WeatherDao = provideCurrentWeatherDatabase(
    context
).weatherDao()

fun provideCurrentWeatherDatabase(context: Context) =
    Room.databaseBuilder(context, WeatherDatabase::class.java, "database-weather").build()