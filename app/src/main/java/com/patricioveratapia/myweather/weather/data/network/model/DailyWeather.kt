package com.patricioveratapia.myweather.weather.data.network.model

data class DailyWeather(
    val clouds: CloudsX,
    val dt: Long,
    val dt_txt: String,
    val main: MainX,
    val sys: SysX,
    val weather: List<WeatherXX>,
    val wind: WindX
)