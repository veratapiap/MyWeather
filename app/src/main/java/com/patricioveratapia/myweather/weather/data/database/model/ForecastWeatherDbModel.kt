package com.patricioveratapia.myweather.weather.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_weather")
data class ForecastWeatherDbModel(
        @PrimaryKey
        val city: String = "",
        @ColumnInfo(name = "dailyWeather")
        val dailyWeather: List<DailyForecastWeatherDbModel> = listOf()
)