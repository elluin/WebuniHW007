package hu.bme.aut.weatherdemo.ui.weather

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.databinding.ActivityWeatherDetailsBinding
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.city_row.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailsActivity : AppCompatActivity() {

    companion object {
        const val KEY_CITY = "KEY_CITY"
    }

    private lateinit var cityName: String
    private lateinit var binding: ActivityWeatherDetailsBinding
    private val weatherViewModel : WeatherDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cityName = intent.getStringExtra(KEY_CITY)!!
        tvCity.text = cityName

        weatherViewModel.getWeatherLiveData().observe(this, {weatherDetailsResult -> render(weatherDetailsResult)})

    }//ONCREATE

    override fun onResume() {
        super.onResume()

        weatherViewModel.getWeatherDetails(cityName)


    }

    private fun processResponse(
        weatherData: WeatherResult?
    ) {
        val icon: String? = weatherData?.weather?.get(0)?.icon
        Glide.with(this@WeatherDetailsActivity)
            .load("https://openweathermap.org/img/w/$icon.png")
            .into(ivWeatherIcon)

        tvMain.text = weatherData?.weather?.get(0)?.main
        tvDescription.text = weatherData?.weather?.get(0)?.description
        tvTemperature.text = getString(R.string.temperature, weatherData?.main?.temp?.toFloat().toString())

        val sdf = SimpleDateFormat("h:mm a z", Locale.getDefault())
        val sunriseDate = Date((weatherData?.sys?.sunrise?.toLong())!! * 1000)
        val sunriseTime = sdf.format(sunriseDate)
        tvSunrise.text = getString(R.string.sunrise, sunriseTime)
        val sunsetDate = Date(weatherData.sys.sunset?.toLong()!! * 1000)
        val sunsetTime = sdf.format(sunsetDate)
        tvSunset.text = getString(R.string.sunset, sunsetTime)
    }


    private fun render(result: WeatherViewState) {
        when (result) {
            is InProgress -> {
                binding.progressLoad.visibility = View.VISIBLE
            }
            is WeatherResponseSuccess -> {
                binding.progressLoad.visibility = View.GONE
                processResponse(result.data)
            }
            is WeatherResponseError -> {
                binding.progressLoad.visibility = View.GONE
                binding.tvMain.text = result.exceptionMsg
            }
        }
    }


}