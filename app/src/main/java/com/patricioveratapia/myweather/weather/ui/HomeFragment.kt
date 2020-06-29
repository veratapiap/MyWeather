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
import org.koin.core.parameter.parametersOf


class HomeFragment(contentLayoutId: Int) : Fragment(contentLayoutId), DialogListener {

    private val homeViewModel: HomeViewModel by viewModel { parametersOf(currentCity) }

    private val currentWeatherView by lazy { fragment_home_current_weather_view }

    private val recyclerView by lazy { fragment_home_forecast_weather_recycler_view }

    private val loadingView by lazy { fragment_home_forecast_weather_loading_view }

    private val errorView by lazy { fragment_home_forecast_weather_error_view }

    private val currentCity: String? by lazy {
        arguments?.getString(EXTRA_CURRENT_CITY, "Buenos Aires")
    }

    private lateinit var weatherForecastAdapter: WeatherForecastAdapter

    companion object {

        private const val EXTRA_CURRENT_CITY = "EXTRA_CURRENT_CITY"

        fun newInstance(currentCity: String? = null): HomeFragment {

            val fragment = HomeFragment(R.layout.fragment_home)

            val args = Bundle()

            args.putString(EXTRA_CURRENT_CITY, currentCity)

            fragment.arguments = args

            return fragment
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
            recyclerView.visibility = View.GONE
            errorView.visibility = View.GONE
            loadingView.visibility = View.GONE

            when (it) {

                is State.Success -> showForecastWeather(it.data)

                is State.Error -> {

                    showForecastWeather(it.data)

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

        recyclerView.scrollToPosition(0)

        hideForecastLoading()

        forecastWeather?.let {

            weatherForecastAdapter.submitList(it)

            recyclerView.visibility = View.VISIBLE

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

        homeViewModel.setCurrentCity(city)

        homeViewModel.getCurrentWeather()

        homeViewModel.getForeCastWeather()
    }

    override fun onCurrentLocationSelected() {

        try {
            (activity as HomeActivity).requestLocationPermission()
        } catch (e: Exception) {

            toast(getString(R.string.error_message))
        }
    }

}