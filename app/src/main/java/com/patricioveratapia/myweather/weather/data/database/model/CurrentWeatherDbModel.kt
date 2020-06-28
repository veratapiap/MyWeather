package com.patricioveratapia.myweather.weather.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherDbModel(
        @PrimaryKey
        val city: String = "",
        @ColumnInfo(name = "main")
        val main: String = "",
        @ColumnInfo(name = "description")
        val description: String = "",
        @ColumnInfo(name = "temperature")
        val temperature: Float = 0f,
        @ColumnInfo(name = "imageCode")
        val imageCode: String = "",
        @ColumnInfo(name = "sunrise")
        val sunrise: Long = 0,
        @ColumnInfo(name = "sunset")
        val sunset: Long = 0,
        @ColumnInfo(name = "date")
        val date: Long = 0,
        @ColumnInfo(name = "timeZone")
        val timeZone: Long = 0
)