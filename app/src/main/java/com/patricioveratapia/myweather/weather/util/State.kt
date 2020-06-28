package com.patricioveratapia.myweather.weather.util

sealed class State<T>(var data: T? = null) {

    class Success<T>(data: T) : State<T>(data)

    class Loading<T> : State<T>()

    class Error<T>(data: T? = null) : State<T>(data)

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "Error"
            is Loading -> "Loading"
        }
    }
}