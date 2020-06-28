package com.patricioveratapia.myweather.weather.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.patricioveratapia.myweather.weather.data.database.model.DailyForecastWeatherDbModel


class DataConverter {

    @TypeConverter
    fun fromDailyForecastWeatherList(list: List<DailyForecastWeatherDbModel>?): String? {

/*            list?.let {
                val gson = Gson()
                val type = object : TypeToken<List<DailyForecastWeatherDbModel>>() {

                }.type
                return gson.toJson(it, type)
            }?: return ""*/

        list?.let {
            val gson = Gson()
            val type = object : TypeToken<List<DailyForecastWeatherDbModel>>() {

            }.type
            return gson.toJson(it, type)
        } ?: return ""

    }

    @TypeConverter
    fun toDailyForecastWeatherList(listString: String?): List<DailyForecastWeatherDbModel>? {

        listString?.let {

            val gson = Gson()
            val type = object : TypeToken<List<DailyForecastWeatherDbModel>>() {

            }.type
            return gson.fromJson<List<DailyForecastWeatherDbModel>>(it, type)
        } ?: return listOf()
    }
}
