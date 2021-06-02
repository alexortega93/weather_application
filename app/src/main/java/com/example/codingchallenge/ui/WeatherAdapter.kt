package com.example.codingchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallenge.data.entities.WeatherList
import com.example.codingchallenge.databinding.ItemWeatherListBinding

class WeatherAdapter(private val listener: (Int) -> Unit): ListAdapter<WeatherList, WeatherAdapter.WeatherViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
        WeatherViewHolder.from(parent)

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int): Unit =
        holder.bind(getItem(position)) {
            listener(position)
        }

    class WeatherViewHolder(private val binding: ItemWeatherListBinding): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): WeatherViewHolder {
                val binding = ItemWeatherListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return WeatherViewHolder(binding)
            }
        }

        fun bind(item: WeatherList, listener: () -> Unit) = with(binding) {
            tvWeatherMain.text = item.weather[0].main
            tvWeatherDescription.text = "(${item.weather[0].description})"
            tvWeatherTemp.text = "Temp: ${item.main.temp}"
            root.setOnClickListener { listener() }
        }
    }
}

private object DiffUtilCallback : DiffUtil.ItemCallback<WeatherList>() {
    override fun areItemsTheSame(oldItem: WeatherList, newItem: WeatherList) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WeatherList, newItem: WeatherList) = oldItem == newItem

}