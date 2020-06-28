package com.patricioveratapia.myweather.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patricioveratapia.myweather.R
import com.patricioveratapia.myweather.weather.extensions.loadImageUrl
import com.patricioveratapia.myweather.weather.ui.model.DailyForecastWeatherUIModel
import kotlinx.android.synthetic.main.list_item_daily_forecast_weather.view.*


class WeatherForecastAdapter() :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DailyForecastWeatherUIModel>() {

        override fun areItemsTheSame(oldItem: DailyForecastWeatherUIModel, newItem: DailyForecastWeatherUIModel): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: DailyForecastWeatherUIModel, newItem: DailyForecastWeatherUIModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return WeatherForecastViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_daily_forecast_weather,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherForecastViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<DailyForecastWeatherUIModel>) {
        differ.submitList(list)
    }

    fun itemAt(position: Int): DailyForecastWeatherUIModel {
        return differ.currentList[position]
    }

    class WeatherForecastViewHolder
    constructor(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: DailyForecastWeatherUIModel) = with(itemView) {

            itemView.list_item_daily_forecast_weather_day_text_view.text = item.day
            itemView.list_item_daily_forecast_weather_time_text_view.text = item.time
            itemView.list_item_daily_forecast_weather_image_view.loadImageUrl(item.imageUrl)
            itemView.list_item_daily_forecast_weather_max_temperature_text_view.text = item.maxTemperature
            itemView.list_item_daily_forecast_weather_min_temperature_text_view.text = item.minTemperature

        }
    }

}