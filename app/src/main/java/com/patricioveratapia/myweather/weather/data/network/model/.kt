package com.patricioveratapia.myweather.weather.data.network.model

data class DayilyWeather(
        val clouds: CloudsX,
        val dt: Long,
        val dt_txt: String,
        val main: MainX,
        val sys: SysX,
        val weather: List<WeatherXX>,
        val wind: WindX
)