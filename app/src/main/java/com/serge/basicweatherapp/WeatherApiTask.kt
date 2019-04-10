package com.serge.basicweatherapp

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherApiTask(private val onTaskComplete: (String?) -> Unit) : AsyncTask<String, Int, String>() {

    override fun doInBackground(vararg params: String): String? {
        var responseData: String? = null

        try {
            val url = URL(BuildConfig.WEATHER_API_URL)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            var reader: BufferedReader? = null

            httpURLConnection.requestMethod = "GET"
            httpURLConnection.doOutput = true
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            try {
                Log.d("WeatherAPITask", "Sending 'GET' request to URL : $url")
                httpURLConnection.responseCode

                reader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
                var data: String? = ""
                while (
                    { data = reader.readLine(); data }() != null
                ) {
                    responseData = data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                httpURLConnection.disconnect()
                reader?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return responseData
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        onTaskComplete.invoke(result)
    }
}



