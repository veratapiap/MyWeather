package com.patricioveratapia.myweather.weather.data.network.model

data class City(
        val coord: CoordX = CoordX(),
        val country: String = "",
        val id: Int = 0,
        val name: String = "",
        val population: Int = 0,
        val sunrise: Int = 0,
        val sunset: Int = 0,
        val timezone: Int = 0
)