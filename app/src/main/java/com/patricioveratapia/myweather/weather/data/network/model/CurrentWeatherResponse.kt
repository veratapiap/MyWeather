package com.patricioveratapia.myweather.weather.data.network.model

data class CurrentWeatherResponse(
        val base: String = "",
        val clouds: Clouds = Clouds(),
        val cod: Int = 0,
        val coord: Coord = Coord(),
        val dt: Long = 0,
        val id: Int = 0,
        val main: Main = Main(),
        val name: String = "",
        val sys: Sys = Sys(),
        val timezone: Long = 0,
        val weather: List<WeatherX> = listOf(),
        val wind: Wind = Wind()
)