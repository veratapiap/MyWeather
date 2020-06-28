package com.patricioveratapia.myweather.weather.data.network.model

data class Main(
        val feels_like: Float = 0f,
        val humidity: Int = 0,
        val pressure: Int = 0,
        val temp: Float = 0f,
        val temp_max: Float = 0f,
        val temp_min: Float = 0f
)