package com.patricioveratapia.myweather.weather.data.network

import com.patricioveratapia.myweather.weather.data.network.model.CurrentWeatherResponse
import com.patricioveratapia.myweather.weather.data.network.model.ForecastWeatherResponse
import com.patricioveratapia.myweather.weather.util.State
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RetrofitService {


    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor) // same for .addInterceptor(...)
        .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCurrentWeather(city: String): State<CurrentWeatherResponse?> {

        return try {
            val result = retrofit
                .create(WeatherApi::class.java)
                .getCurrentWeather(city, API_KEY, "metric", Locale.getDefault().language)
                .execute()

            State.Success(result.body())
        } catch (e: Exception) {

            State.Error()
        }
    }

    fun getForecastWeather(city: String): State<ForecastWeatherResponse?> {

        return try {
            val result = retrofit
                .create(WeatherApi::class.java)
                .getForecastWeather(city, API_KEY, "metric", Locale.getDefault().language)
                .execute()

            State.Success(result.body())
        } catch (e: Exception) {

            State.Error()
        }
    }


    companion object {

        const val BASE_URL = "https://api.openweathermap.org"

        const val API_KEY = "a25e6423bbc3b90481ff70596d5de47c"

        const val BASE_IMAGE_URL = "http://openweathermap.org/img/wn/{imageCode}@2x.png"
    }

}
