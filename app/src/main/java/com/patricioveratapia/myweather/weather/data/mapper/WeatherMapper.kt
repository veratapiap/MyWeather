package com.patricioveratapia.myweather.weather.data.mapper

import com.patricioveratapia.myweather.weather.data.database.model.CurrentWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.DailyForecastWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.ForecastWeatherDbModel
import com.patricioveratapia.myweather.weather.data.network.model.CurrentWeatherResponse
import com.patricioveratapia.myweather.weather.data.network.model.ForecastWeatherResponse

class WeatherMapper {

    fun mapCurrentWeather(input: CurrentWeatherResponse?): CurrentWeatherDbModel {

        return input?.let {

            CurrentWeatherDbModel(
                city = it.name,
                main = it.weather[0].main,
                description = it.weather[0].description,
                temperature = it.main.temp,
                imageCode = it.weather[0].icon,
                sunrise = it.sys.sunrise,
                sunset = it.sys.sunset,
                date = it.dt,
                timeZone = it.timezone
            )
        } ?: CurrentWeatherDbModel()

    }

    fun mapForecastWeather(input: ForecastWeatherResponse?): ForecastWeatherDbModel {

        return input?.let {

            ForecastWeatherDbModel(
                city = it.city.name,
                dailyWeather = it.list.map { dailyWeather ->
                    DailyForecastWeatherDbModel(
                        day = dailyWeather.dt,
                        maxTemperature = dailyWeather.main.temp_max.toInt(),
                        minTemperature = dailyWeather.main.temp_min.toInt(),
                        imageCode = dailyWeather.weather[0].icon
                    )
                }
            )
        } ?: ForecastWeatherDbModel()

    }
}