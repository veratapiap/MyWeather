package com.patricioveratapia.myweather.weather.ui.interfaces

import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun getCities(): Flow<State<List<String>>>
}