package com.serge.basicweatherapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.serge.basicweatherapp.data.WeatherApiResponse
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        WeatherApiTask(::onTaskComplete).execute()
    }

    fun onTaskComplete(data: String?) {
        val weatherResponse = WeatherApiResponse(data!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
