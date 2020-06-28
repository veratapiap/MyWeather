package com.patricioveratapia.myweather.weather.data

import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun getCities(): Flow<State<List<String>>>
}