package com.serge.basicweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serge.basicweatherapp.data.WeatherApiResponse
import com.serge.basicweatherapp.ui.ProgressBarDialog
import com.serge.basicweatherapp.ui.ProgressBarDialog.Companion.PROGRESS_BAR_TAG
import kotlinx.android.synthetic.main.activity_scrolling.*

open class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        showProgressBar()

        WeatherApiTask(::onTaskComplete).execute()
    }

    private fun onTaskComplete(data: String?) {
        hideProgressBar()

        val weatherResponse = WeatherApiResponse(data!!)
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
