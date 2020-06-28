package com.patricioveratapia.myweather.weather.data.network

import com.patricioveratapia.myweather.weather.data.network.model.CurrentWeatherResponse
import com.patricioveratapia.myweather.weather.data.network.model.ForecastWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(GET_CURRENT_WEATHER)
    fun getCurrentWeather(
            @Query("q") city: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String,
            @Query("lang") lang: String
    ): Call<CurrentWeatherResponse>

    @GET(GET_FORECAST_WEATHER)
    fun getForecastWeather(
            @Query("q") city: String,
            @Query("appid") apiKey: String,
            @Query("units") units: String,
            @Query("lang") lang: String
    ): Call<ForecastWeatherResponse>


    companion object {

        const val GET_CURRENT_WEATHER = "/data/2.5/weather"
        const val GET_FORECAST_WEATHER = "/data/2.5/forecast"
    }
}