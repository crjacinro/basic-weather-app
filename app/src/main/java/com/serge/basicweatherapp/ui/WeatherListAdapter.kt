package com.serge.basicweatherapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.serge.basicweatherapp.R
import com.serge.basicweatherapp.data.WeatherView

const val FEATURED_INDEX = 0

class WeatherListAdapter(
    private val context: Context,
    private val dataSource: List<WeatherView>
) : BaseAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount() = dataSource.size
    override fun getItem(position: Int) = dataSource[position]
    override fun getItemId(position: Int) = position.toLong()
    override fun getViewTypeCount() = 2
    override fun getItemViewType(position: Int) = dataSource[position].type

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val vh: ViewHolder?
        val listItem = dataSource[position]
        val viewType = getItemViewType(position)
        var view: View? = convertView

        if (convertView == null) {
            if (viewType == FEATURED_INDEX) {
                view = inflater.inflate(R.layout.featured_weather_card, null)
            } else {
                view = inflater.inflate(R.layout.forecasted_weather_card, null)
            }

            vh = ViewHolder(view)
            view.tag = vh
        } else {
            vh = (view?.tag) as ViewHolder
        }

        vh.heading?.text = listItem.title
        vh.description?.text = listItem.description

        if (position == 0) {
            vh.temperature?.text = listItem.temperature
            vh.card?.background = listItem.bgDrawable
        }

        return view!!
    }

    private class ViewHolder(rowView: View?) {
        val heading: TextView? = rowView?.findViewById(R.id.heading)
        val description: TextView? = rowView?.findViewById(R.id.description)

        val temperature: TextView? = rowView?.findViewById(R.id.temperature)
        val card: ImageView? = rowView?.findViewById(R.id.featured_card_image)
    }
}