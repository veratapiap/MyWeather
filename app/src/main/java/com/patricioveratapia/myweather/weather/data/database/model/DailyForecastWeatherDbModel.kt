package com.patricioveratapia.myweather.weather.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_forecast_weather")
data class DailyForecastWeatherDbModel(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        @ColumnInfo(name = "day")
        val day: Long = 0,
        @ColumnInfo(name = "maxTemperature")
        val maxTemperature: Int = 0,
        @ColumnInfo(name = "minTemperature")
        val minTemperature: Int = 0,
        @ColumnInfo(name = "imageCode")
        val imageCode: String = ""
)