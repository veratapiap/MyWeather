package com.patricioveratapia.myweather.weather.data.mapper

import com.patricioveratapia.myweather.weather.data.database.model.CurrentWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.ForecastWeatherDbModel
import com.patricioveratapia.myweather.weather.extensions.*
import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel

class WeatherDatabaseMapper {

    fun mapCurrentWeather(input: CurrentWeatherDbModel): CurrentWeatherUIModel {

        return CurrentWeatherUIModel(
            input.city,
            input.temperature.toInt(),
            input.main,
            input.description,
            (input.date > input.sunrise && input.date < input.sunset),
            input.imageCode.buildImageUrl(),
            (input.date).time()
        )
    }

    fun mapForecastWeather(input: ForecastWeatherDbModel?): List<DailyForecastWeatherUIModel> {

        return input?.dailyWeather?.filter { it.day * 1000 > System.currentTimeMillis() }?.map {
            DailyForecastWeatherUIModel(
                it.day.dayOfWeek().capitalize(),
                it.day.hours(),
                it.maxTemperature.toTemperature(),
                it.minTemperature.toTemperature(),
                it.imageCode.buildImageUrl()
            )
        }.orEmpty()
    }
}