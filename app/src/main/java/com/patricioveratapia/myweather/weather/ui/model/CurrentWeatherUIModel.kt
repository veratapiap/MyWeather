package com.patricioveratapia.myweather.weather.ui.model

data class CurrentWeatherUIModel(
        val cityName: String = "",
        val currentTemperature: Int = 0,
        val currentWeather: String = "",
        val currentWeatherDescription: String = "",
        val isDaytime: Boolean = true,
        val imageUrl: String = "",
        val date: String = ""
)