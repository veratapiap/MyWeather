package com.patricioveratapia.myweather.weather.extensions

import com.patricioveratapia.myweather.weather.data.network.RetrofitService
import java.text.SimpleDateFormat
import java.util.*

fun String.buildImageUrl(): String {
    return RetrofitService.BASE_IMAGE_URL.replace("{imageCode}", this)
}

fun Long.dayOfWeek(): String {

    val date = Date(this * 1000)

    val simpleDateFormat = SimpleDateFormat("E", Locale.getDefault())

    return simpleDateFormat.format(date).replace(".", "")
}

fun Long.time(): String {

    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    return simpleDateFormat.format(Date(this * 1000))
}

fun Long.hours(): String {

    val simpleDateFormat = SimpleDateFormat("HH", Locale.getDefault())

    return simpleDateFormat.format(Date(this * 1000)) + "Hs"
}

fun Int.toTemperature(): String {

    return "$this°"
}

