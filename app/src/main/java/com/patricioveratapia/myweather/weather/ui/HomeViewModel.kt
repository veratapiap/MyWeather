package com.patricioveratapia.myweather.weather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patricioveratapia.myweather.weather.ui.interfaces.WeatherRepository
import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: WeatherRepository, var location: String) : ViewModel() {

    private val _currentWeather = MutableLiveData<State<CurrentWeatherUIModel>>()

    val currentWeather: LiveData<State<CurrentWeatherUIModel>> = _currentWeather

    private val _forecastWeather = MutableLiveData<State<List<DailyForecastWeatherUIModel>>>()

    val forecastWeather: LiveData<State<List<DailyForecastWeatherUIModel>>> = _forecastWeather

    private var currentCity = location

    init {

        getCurrentWeather()

        getForeCastWeather()
    }

    fun getCurrentWeather() {

        viewModelScope.launch(IO) {

            repository.getCurrentWeather(currentCity).collect {

                _currentWeather.postValue(it)
            }
        }
    }

    fun getForeCastWeather() {

        viewModelScope.launch(IO) {

            repository.getForecastWeather(currentCity).collect {

                _forecastWeather.postValue(it)
            }
        }
    }

    fun setCurrentCity(city: String) {

        currentCity = city
    }

}

