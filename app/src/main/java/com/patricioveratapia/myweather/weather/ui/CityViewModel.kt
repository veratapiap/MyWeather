package com.patricioveratapia.myweather.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patricioveratapia.myweather.weather.ui.interfaces.CityRepository
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CityViewModel(private val repository: CityRepository) : ViewModel() {

    private val _cities = MutableLiveData<State<List<String>>>()

    val cities: LiveData<State<List<String>>> = _cities

    init {

        getCities()

    }

    private fun getCities() {

        viewModelScope.launch(Dispatchers.IO) {

            repository.getCities().collect {

                _cities.postValue(it)
            }
        }

    }
}

