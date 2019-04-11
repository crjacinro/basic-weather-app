package com.serge.basicweatherapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serge.basicweatherapp.data.WeatherApiResponse
import com.serge.basicweatherapp.data.WeatherView
import com.serge.basicweatherapp.ui.ProgressBarDialog
import com.serge.basicweatherapp.ui.ProgressBarDialog.Companion.PROGRESS_BAR_TAG
import com.serge.basicweatherapp.ui.WeatherListAdapter
import kotlinx.android.synthetic.main.content_scrolling.*
import java.text.SimpleDateFormat
import java.util.*

open class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        showProgressBar()

        WeatherApiTask(::onTaskComplete).execute()
    }

    private fun onTaskComplete(data: String?) {
        hideProgressBar()

        val weatherResponse = WeatherApiResponse(data!!)

        intializeView(weatherResponse)
    }

    private fun intializeView(weatherResponse: WeatherApiResponse) {
        val adapter = WeatherListAdapter(this, mapResponseToView(weatherResponse))

        weather_list.adapter = adapter
    }

    private fun mapResponseToView(weatherResponse: WeatherApiResponse): List<WeatherView> {
        val weatherView = mutableListOf<WeatherView>()

        weatherResponse.data?.forEachIndexed { index, it ->
            val featuredTitle = generateFeaturedTitle(it.datetime)
            val forecastTitle = generateForecastTitle(it.datetime)

            weatherView.add(
                WeatherView(
                    if (index == 0) featuredTitle else forecastTitle,
                    it.temp.toString() + " \u2103",
                    it.weather.description!!,
                    generateDrawable(),
                    if (index == 0) index else 1
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

    private fun generateDrawable(): Drawable {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..4 -> applicationContext.getDrawable(R.mipmap.night_bg)!!
            in 5..18 -> applicationContext.getDrawable(R.mipmap.morning_bg)!!
            in 19..23 -> applicationContext.getDrawable(R.mipmap.night_bg)!!
            else -> {
                applicationContext.getDrawable(R.mipmap.morning_bg)!!
            }
        }
    }

    private fun showProgressBar() {
        if (supportFragmentManager.findFragmentByTag(PROGRESS_BAR_TAG) != null) {
            return
        }
        ProgressBarDialog.newInstance().show(supportFragmentManager, PROGRESS_BAR_TAG)
    }

    private fun hideProgressBar() {
        val loadingIndicatorFragment = supportFragmentManager.findFragmentByTag(PROGRESS_BAR_TAG) as ProgressBarDialog?
        loadingIndicatorFragment?.dismiss()
    }
}
