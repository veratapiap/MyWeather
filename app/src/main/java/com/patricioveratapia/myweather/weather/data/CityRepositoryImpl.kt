package com.patricioveratapia.myweather.weather.data

import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class CityRepositoryImpl : CityRepository {

    override fun getCities(): Flow<State<List<String>>> {

        return flow {

            when (val state = getMockedCities()) {

                is State.Success -> emit(state)

                is State.Loading -> {
                }

                is State.Error -> {
                }
            }

        }.onStart { emit(State.Loading()) }

    }

    private fun getMockedCities(): State<List<String>> {

        return State.Success(
                listOf("Buenos Aires", "Tandil", "La Plata", "Necochea", "Balcarce", "Rosario", "Rawson", "Posadas", "Río Gallegos","Tucumán","Resistencia")

        )
    }
}