package com.serge.basicweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serge.basicweatherapp.data.WeatherApiResponse
import com.serge.basicweatherapp.data.WeatherView
import com.serge.basicweatherapp.ui.ProgressBarDialog
import com.serge.basicweatherapp.ui.ProgressBarDialog.Companion.PROGRESS_BAR_TAG
import com.serge.basicweatherapp.ui.WeatherListAdapter
import kotlinx.android.synthetic.main.content_scrolling.*

open class WeatherActivity : AppCompatActivity(), WeatherListAdapter.AdapterEvent {

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

    override fun onItemClicked(weatherView: WeatherView) {
        startActivity(WeatherDetailsActivity.newIntent(this, weatherView))
    }

    private fun intializeView(weatherResponse: WeatherApiResponse) {
        val adapter = WeatherListAdapter(this, weatherResponse.mapResponseToView(applicationContext), this)

        weather_list.adapter = adapter
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
