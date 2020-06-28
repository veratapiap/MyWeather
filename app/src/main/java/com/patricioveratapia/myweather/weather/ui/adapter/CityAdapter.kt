package com.patricioveratapia.myweather.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patricioveratapia.myweather.R
import kotlinx.android.synthetic.main.list_item_city.view.*

class CityAdapter :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onCityClickListener: ((city: String) -> Unit)? = null

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CityViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_item_city,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityViewHolder -> {

                holder.itemView.setOnClickListener { onCityClickListener?.invoke(differ.currentList[position]) }

                holder.bind(differ.currentList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<String>) {
        differ.submitList(list)
    }

    fun itemAt(position: Int): String {
        return differ.currentList[position]
    }

    class CityViewHolder
    constructor(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: String, position: Int) = with(itemView) {

            itemView.list_item_city_name_text_view.text = item


            val colorBackground = if (position % 2 == 0) ContextCompat.getColor(this.context, R.color.colorBackground)
            else ContextCompat.getColor(this.context, R.color.colorWhite)

            itemView.setBackgroundColor(colorBackground)


        }
    }
}