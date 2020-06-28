package com.patricioveratapia.myweather.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.patricioveratapia.myweather.weather.data.database.converter.DataConverter
import com.patricioveratapia.myweather.weather.data.database.model.CurrentWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.ForecastWeatherDbModel

@Database(entities = [CurrentWeatherDbModel::class, ForecastWeatherDbModel::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}
