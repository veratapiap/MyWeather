package com.patricioveratapia.myweather

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.patricioveratapia.myweather.weather.data.WeatherRepositoryImpl
import com.patricioveratapia.myweather.weather.data.database.WeatherDao
import com.patricioveratapia.myweather.weather.data.database.model.CurrentWeatherDbModel
import com.patricioveratapia.myweather.weather.data.database.model.ForecastWeatherDbModel
import com.patricioveratapia.myweather.weather.data.mapper.WeatherDatabaseMapper
import com.patricioveratapia.myweather.weather.data.mapper.WeatherMapper
import com.patricioveratapia.myweather.weather.data.network.RetrofitService
import com.patricioveratapia.myweather.weather.data.network.model.CurrentWeatherResponse
import com.patricioveratapia.myweather.weather.data.network.model.ForecastWeatherResponse
import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeatherRepositoryImplTest {

    lateinit var repository: WeatherRepositoryImpl
    val service: RetrofitService = mock()
    val mapper: WeatherMapper = mock()
    val databaseMapper: WeatherDatabaseMapper = mock()
    val weatherDao: WeatherDao = mock()


    @Before
    fun setup() {

        repository =
            WeatherRepositoryImpl(
                service,
                mapper,
                databaseMapper,
                weatherDao
            )
    }

    @Test
    fun whenDataIsCachedShouldReturnsSuccessState() = runBlocking {

        val city: String = "Buenos Aires"

        val currentWeather: CurrentWeatherDbModel = mock()

        val expectedResult: CurrentWeatherUIModel = mock()

        val currentWeatherNetwork: CurrentWeatherResponse? = mock()

        whenever(weatherDao.getCurrentWeather(city)) doReturn currentWeather

        whenever(service.getCurrentWeather(city)) doReturn State.Success(currentWeatherNetwork)

        whenever(databaseMapper.mapCurrentWeather(currentWeather)) doReturn expectedResult

        whenever(mapper.mapCurrentWeather(currentWeatherNetwork)) doReturn currentWeather

        val actualResult = repository.getCurrentWeather(city)

        val flowList = actualResult.toList()

        assertEquals(3, flowList.size)

        assertTrue(flowList[0] is State.Loading)

        assertTrue(flowList[1] is State.Success)

        assertTrue(flowList[2] is State.Success)

        assertEquals(expectedResult, flowList[1].data)

        assertEquals(expectedResult, flowList[2].data)

    }

    @Test
    fun whenDataIsNotCachedShouldReturnsNetworkSuccessState() = runBlocking {

        val city: String = "Buenos Aires"

        val forecastWeatherDbModel: ForecastWeatherDbModel = mock()

        val forecastWeatherNetwork: ForecastWeatherResponse? = mock()

        val expectedResult: List<DailyForecastWeatherUIModel> = listOf(
            DailyForecastWeatherUIModel(
                "day",
                "time",
                "max",
                "min",
                "ïmage"
            )
        )

        whenever(weatherDao.getForecastWeather(city)) doReturn null doReturn forecastWeatherDbModel

        whenever(service.getForecastWeather(city)) doReturn State.Success(forecastWeatherNetwork)

        whenever(mapper.mapForecastWeather(forecastWeatherNetwork)) doReturn forecastWeatherDbModel

        whenever(databaseMapper.mapForecastWeather(forecastWeatherDbModel)) doReturn expectedResult

        val result = repository.getForecastWeather(city)

        val flowList = result.toList()

        assertEquals(2, flowList.size)

        assertTrue(flowList[0] is State.Loading)

        assertTrue(flowList[1] is State.Success)

        assertEquals(expectedResult, flowList[1].data)

        assertEquals("day", flowList[1].data?.get(0)?.day)

        assertEquals("time", flowList[1].data?.get(0)?.time)

        assertEquals("max", flowList[1].data?.get(0)?.maxTemperature)

        assertEquals("min", flowList[1].data?.get(0)?.minTemperature)

    }

    @Test
    fun whenDataIsNotCachedShouldReturnsNetworkErrorState() = runBlocking {

        val city: String = "Buenos Aires"

        whenever(weatherDao.getForecastWeather(city)) doReturn null

        whenever(service.getForecastWeather(city)) doReturn State.Error()

        val result = repository.getForecastWeather(city)

        val flowList = result.toList()

        assertEquals(2, flowList.size)

        assertTrue(flowList[0] is State.Loading)

        assertTrue(flowList[1] is State.Error)

    }
}