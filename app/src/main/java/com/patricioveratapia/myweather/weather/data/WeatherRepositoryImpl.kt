package com.patricioveratapia.myweather.weather.data

import com.patricioveratapia.myweather.weather.data.database.WeatherDao
import com.patricioveratapia.myweather.weather.data.mapper.WeatherDatabaseMapper
import com.patricioveratapia.myweather.weather.data.mapper.WeatherMapper
import com.patricioveratapia.myweather.weather.data.network.RetrofitService
import com.patricioveratapia.myweather.weather.ui.interfaces.WeatherRepository
import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class WeatherRepositoryImpl(
    private val service: RetrofitService,
    private val mapper: WeatherMapper,
    private val databaseMapper: WeatherDatabaseMapper,
    private val weatherDao: WeatherDao
) : WeatherRepository {


    override fun getCurrentWeather(city: String): Flow<State<CurrentWeatherUIModel>> {

        return flow {

            var databaseResult = weatherDao.getCurrentWeather(city)

            databaseResult?.apply { emit(State.Success(databaseMapper.mapCurrentWeather(databaseResult))) }

            when (val weatherState = service.getCurrentWeather(city)) {

                is State.Success -> {

                    val databaseModel = mapper.mapCurrentWeather(weatherState.data)

                    weatherDao.deleteCurrentWeather(city)

                    weatherDao.saveCurrentWeather(databaseModel)

                    emit(
                        State.Success(
                            databaseMapper.mapCurrentWeather(databaseModel)

                        )
                    )
                }

                is State.Error -> emit(
                    State.Error<CurrentWeatherUIModel>(
                        databaseMapper?.mapCurrentWeather(
                            weatherDao.getCurrentWeather(
                                city
                            )
                        )
                    )
                )

                is State.Loading -> {
                }
            }

        }.onStart { emit(State.Loading()) }
    }

    override fun getForecastWeather(city: String): Flow<State<List<DailyForecastWeatherUIModel>>> {

        return flow {

            var databaseResult = weatherDao.getForecastWeather(city)

            databaseResult?.apply { emit(State.Success(databaseMapper.mapForecastWeather(databaseResult))) }

            when (val weatherState = service.getForecastWeather(city)) {

                is State.Success -> {

                    val databaseModel = mapper.mapForecastWeather(weatherState.data)

                    weatherDao.saveForecastWeather(databaseModel)

                    emit(
                        State.Success(
                            databaseMapper.mapForecastWeather(weatherDao.getForecastWeather(city))

                        )
                    )
                }

                is State.Error -> emit(
                    State.Error<List<DailyForecastWeatherUIModel>>(
                        databaseMapper.mapForecastWeather(
                            weatherDao.getForecastWeather(
                                city
                            )
                        )
                    )
                )

                is State.Loading -> {
                }
            }

        }.onStart { emit(State.Loading()) }
    }

    override fun refreshWeather(city: String): Flow<State<CurrentWeatherUIModel>> {

        return flow {

            when (val weatherState = service.getCurrentWeather(city)) {

                is State.Success -> {

                    val databaseModel = mapper.mapCurrentWeather(weatherState.data)

                    weatherDao.deleteCurrentWeather(city)

                    weatherDao.saveCurrentWeather(databaseModel)

                    emit(
                        State.Success(
                            databaseMapper.mapCurrentWeather(databaseModel)

                        )
                    )
                }

                is State.Error -> emit(
                    State.Error<CurrentWeatherUIModel>(
                        databaseMapper.mapCurrentWeather(
                            weatherDao.getCurrentWeather(
                                city
                            )
                        )
                    )
                )

                is State.Loading -> {
                }
            }

        }.onStart { emit(State.Loading()) }
    }

}