package com.patricioveratapia.myweather.weather.ui.model

data class DailyForecastWeatherUIModel(
        val day: String = "",
        val time: String = "",
        val maxTemperature: String = "",
        val minTemperature: String = "",
        val imageUrl: String = ""
)