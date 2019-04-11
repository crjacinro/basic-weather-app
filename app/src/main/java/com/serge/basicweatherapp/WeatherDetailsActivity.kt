package com.serge.basicweatherapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.serge.basicweatherapp.data.WeatherView
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.content_weather_details.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

const val DATE_KEY = "DATE_KEY"
const val TEMPERATURE_KEY = "TEMPERATURE_KEY"
const val WIND_SPEED_KEY = "WIND_SPEED_KEY"
const val WIND_DIRECTION_KEY = "WIND_DIRECTION_KEY"
const val PRESSURE_KEY = "PRESSURE_KEY"
const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
const val LAST_UPDATED_KEY = "LAST_UPDATED_KEY"
const val LOCATION_KEY = "LOCATION_KEY"

class WeatherDetailsActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context, weatherView: WeatherView): Intent {
            val intent = Intent(context, WeatherDetailsActivity::class.java)

            intent.putExtra(DATE_KEY, weatherView.featureTitle)
            intent.putExtra(LOCATION_KEY, weatherView.location)
            intent.putExtra(TEMPERATURE_KEY, weatherView.temperature)
            intent.putExtra(WIND_SPEED_KEY, weatherView.wind_speed)
            intent.putExtra(WIND_DIRECTION_KEY, weatherView.wind_direction)
            intent.putExtra(PRESSURE_KEY, weatherView.pressure)
            intent.putExtra(DESCRIPTION_KEY, weatherView.description)
            intent.putExtra(LAST_UPDATED_KEY, weatherView.ts)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        setSupportActionBar(toolbar)

        val date = intent.getStringExtra(DATE_KEY)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = date

        val location = intent.getStringExtra(LOCATION_KEY)
        val temperature = intent.getStringExtra(TEMPERATURE_KEY)
        val windSpeed = intent.getDoubleExtra(WIND_SPEED_KEY, 0.0)
        val windDirection = intent.getStringExtra(WIND_DIRECTION_KEY)
        val pressure = intent.getDoubleExtra(PRESSURE_KEY, 0.0)
        val description = intent.getStringExtra(DESCRIPTION_KEY)
        val lastUpdated = intent.getLongExtra(LAST_UPDATED_KEY, 0)

        content_image.setImageResource(R.mipmap.content_bg)
        location_label.text = location
        temp_label.text = temperature
        wind_label.text = "Wind speed: ${generateWindeSpeedLabel(windSpeed)} m/s $windDirection"
        pressure_label.text = "Pressure: $pressure mb"
        description_label.text = description
        last_updated_label.text = generateLastUpdatedLabel(lastUpdated)
    }

    private fun generateLastUpdatedLabel(ts: Long): String {
        val date = Date(ts)
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val formattedDate = dateFormatter.format(date)

        return "Last Updated: $formattedDate"
    }

    private fun generateWindeSpeedLabel(windSpeed: Double): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(windSpeed).toFloat()
    }

}
