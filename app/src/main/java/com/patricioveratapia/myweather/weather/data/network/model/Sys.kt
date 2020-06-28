package com.patricioveratapia.myweather.weather.data.network.model

data class Sys(
        val country: String = "",
        val id: Int = 0,
        val sunrise: Long = 0,
        val sunset: Long = 0,
        val type: Int = 0
)