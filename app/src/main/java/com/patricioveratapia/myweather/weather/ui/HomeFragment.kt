package com.patricioveratapia.myweather.weather.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.patricioveratapia.myweather.R
import com.patricioveratapia.myweather.weather.extensions.toast
import com.patricioveratapia.myweather.weather.ui.adapter.WeatherForecastAdapter
import com.patricioveratapia.myweather.weather.ui.interfaces.DialogListener
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment(contentLayoutId: Int) : Fragment(contentLayoutId), DialogListener {


    private val homeViewModel: HomeViewModel by viewModel()

    private val currentWeatherView by lazy { fragment_home_current_weather_view }

    val recyclerView by lazy { fragment_home_forecast_weather_recycler_view }

    val loadingView by lazy { fragment_home_forecast_weather_loading_view }

    val errorView by lazy { fragment_home_forecast_weather_error_view }


    private lateinit var weatherForecastAdapter: WeatherForecastAdapter

    companion object {

        fun newInstance(): HomeFragment {

            return HomeFragment(R.layout.fragment_home)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupCurrentWeather()

        setupForecastWeather()

        observeCurrentWeather()

        observeForecastWeather()

    }


    private fun setupCurrentWeather() {

        currentWeatherView.onRefreshListener = {

            homeViewModel.getCurrentWeather()
        }

        currentWeatherView.onErrorListener = {

            this.toast(getString(R.string.error_message))
        }
    }


    private fun setupForecastWeather() {

        errorView.setOnClickListener {

            homeViewModel.getForeCastWeather()
        }
    }


    private fun setupRecyclerView() {

        weatherForecastAdapter = WeatherForecastAdapter()

        recyclerView.adapter = weatherForecastAdapter

    }

    private fun observeCurrentWeather() {

        homeViewModel.currentWeather.observe(viewLifecycleOwner, Observer {

            currentWeatherView.setState(it)
        })
    }

    private fun observeForecastWeather() {

        homeViewModel.forecastWeather.observe(viewLifecycleOwner, Observer {

            when (it) {

                is State.Success -> showForecastWeather(it.data)

                is State.Error -> {

                    showForecastWeather(it.data)

                    showErrorState()
                }

                is State.Loading -> showForecastLoading()

            }
        })
    }

    private fun showErrorState() {

        hideForecastLoading()

        this.toast(getString(R.string.error_message))

        errorView.visibility = View.VISIBLE
    }

    private fun showForecastWeather(forecastWeather: List<DailyForecastWeatherUIModel>?) {

        hideForecastLoading()

        forecastWeather?.let {

            weatherForecastAdapter.submitList(it)
        } ?: showErrorState()
    }

    private fun showForecastLoading() {

        loadingView.visibility = View.VISIBLE
    }

    private fun hideForecastLoading() {

        loadingView.visibility = View.GONE
    }

    fun showCitySelectionDialog() {

        SelectCityDialogFragment.newInstance().show(childFragmentManager, null)

    }

    override fun onCitySelected(city: String) {

        homeViewModel.setCity(city)

        homeViewModel.getCurrentWeather()

        homeViewModel.getForeCastWeather()
    }

    override fun onCurrentLocationSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}