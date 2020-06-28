package com.patricioveratapia.myweather.weather.data

import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(city: String): Flow<State<CurrentWeatherUIModel>>

    fun getForecastWeather(city: String): Flow<State<List<DailyForecastWeatherUIModel>>>
}