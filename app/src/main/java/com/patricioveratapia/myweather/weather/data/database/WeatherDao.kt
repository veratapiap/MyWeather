package com.patricioveratapia.myweather.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patricioveratapia.myweather.weather.data.database.model.CurrentWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.ForecastWeatherDbModel

@Dao
interface WeatherDao {


    @Query("SELECT * from current_weather where city=:city")
    fun getCurrentWeather(city: String): CurrentWeatherDbModel

    @Query("SELECT * from forecast_weather where city=:city")
    fun getForecastWeather(city: String): ForecastWeatherDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeather(currentWeather: CurrentWeatherDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecastWeather(forecastWeather: ForecastWeatherDbModel)

    @Query("DELETE from current_weather where city=:city")
    fun deleteCurrentWeather(city: String)
}