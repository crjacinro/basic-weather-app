package com.serge.basicweatherapp.data

import android.content.Context
import android.graphics.drawable.Drawable
import com.serge.basicweatherapp.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WeatherApiResponse(json: String) : JSONObject(json) {
    val city_name: String? = this.optString("city_name")
    val data = this.optJSONArray("data")
        ?.let {
            0.until(it.length()).map { i -> it.optJSONObject(i) }
        }?.map {
            WeatherData(it.toString())
        }


    fun mapResponseToView(context: Context): List<WeatherView> {
        val weatherView = mutableListOf<WeatherView>()

        this.data?.forEachIndexed { index, it ->
            val featuredTitle = generateFeaturedTitle(it.datetime)
            val forecastTitle = generateForecastTitle(it.datetime)

            weatherView.add(
                WeatherView(
                    this.city_name!!,
                    featuredTitle,
                    forecastTitle,
                    it.temp.toString() + " \u2103",
                    it.weather.description!!,
                    generateDrawable(context),
                    if (index == 0) index else 1,
                    Date().time,
                    it.wind_spd,
                    it.wind_cdir_full,
                    it.pres
                )
            )
        }

        return weatherView
    }

    private fun generateFeaturedTitle(dateValue: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateValue)
        val dateFormatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.US)
        return dateFormatter.format(date)
    }

    private fun generateForecastTitle(dateValue: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateValue)
        val dateFormatter = SimpleDateFormat("E, MM dd, yyyy", Locale.US)
        return dateFormatter.format(date)
    }

    private fun generateDrawable(context: Context): Drawable {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..4 -> context.getDrawable(R.mipmap.night_bg)!!
            in 5..18 -> context.getDrawable(R.mipmap.morning_bg)!!
            in 19..23 -> context.getDrawable(R.mipmap.night_bg)!!
            else -> {
                context.getDrawable(R.mipmap.morning_bg)!!
            }
        }
    }
}

class WeatherData(json: String) : JSONObject(json) {
    val temp = this.optDouble("temp")
    val datetime = this.optString("datetime")
    val ts = this.optLong("ts")
    val pres = this.optDouble("pres")
    val wind_spd = this.optDouble("wind_spd")
    val wind_cdir_full = this.optString("wind_cdir_full")
    val weather = Weather(this.optJSONObject("weather").toString())
}

class Weather(json: String) : JSONObject(json) {
    val description: String? = this.optString("description")
}