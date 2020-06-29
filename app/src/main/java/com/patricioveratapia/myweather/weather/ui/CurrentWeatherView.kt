package com.patricioveratapia.myweather.weather.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import com.patricioveratapia.myweather.R
import com.patricioveratapia.myweather.weather.extensions.loadImageUrl
import com.patricioveratapia.myweather.weather.extensions.setRipple
import com.patricioveratapia.myweather.weather.ui.model.CurrentWeatherUIModel
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.android.synthetic.main.view_current_weather.view.*

class CurrentWeatherView : FrameLayout {

    private val cardViewContainer by lazy { view_current_weather_card_view }
    private val textViewCityName by lazy { view_current_weather_city_text_view }
    private val textViewWeather by lazy { view_current_weather_text_view }
    private val imageViewWeather by lazy { view_current_weather_image_view }
    private val textViewTemperature by lazy { view_current_weather_temperature_text_view }
    private val viewContentState by lazy { view_current_weather_content }
    private val refreshView by lazy { view_current_weather_refresh_view }
    private val textViewLastUpdate by lazy { view_current_weather_last_update_text_view }
    private val viewLoadingState by lazy { view_current_weather_loading_view }


    var onRefreshListener: (() -> Unit)? = null
    var onErrorListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {

        initView(context)
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        initView(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {

        initView(context)
    }

    private fun initView(context: Context) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.view_current_weather, this, true)

        setupRefresh()


    }


    private fun setupRefresh() {

        refreshView.setRipple()

        refreshView.setOnClickListener {

            onRefreshListener?.invoke()
        }
    }

    fun setState(state: State<CurrentWeatherUIModel>) {

        viewLoadingState.visibility = View.INVISIBLE

        viewContentState.visibility = View.GONE

        when (state) {

            is State.Loading -> viewLoadingState.visibility = View.VISIBLE

            is State.Error -> {

                onErrorListener?.invoke()

                setData(state.data)
            }

            is State.Success -> {

                viewContentState.visibility = View.VISIBLE

                setData(state.data)
            }
        }

    }

    private fun setData(data: CurrentWeatherUIModel?) {

        data?.let {

            textViewCityName.text = it.cityName

            textViewWeather.text = it.currentWeatherDescription.capitalize()

            textViewTemperature.text = it.currentTemperature.toString()

            imageViewWeather.loadImageUrl(it.imageUrl)

            var lastUpdate = context.getString(R.string.last_update) + " " + it.date

            textViewLastUpdate.text = lastUpdate

            if (data.isDaytime) {

                cardViewContainer.setCardBackgroundColor(resources.getColor(R.color.colorDayLight, null))

            } else {

                cardViewContainer.setCardBackgroundColor(resources.getColor(R.color.colorPrimaryDark, null))

            }

            viewContentState.visibility = View.VISIBLE

        }
    }
}
