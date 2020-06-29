package com.patricioveratapia.myweather.weather.data.network.model

data class ForecastWeatherResponse(
    val city: City = City(),
    val cnt: Int = 0,
    val cod: String = "",
    val list: List<DailyWeather> = listOf(),
    val message: Int = 0
)