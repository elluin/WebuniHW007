package hu.bme.aut.weatherdemo.repository.weather

import androidx.lifecycle.MutableLiveData
import hu.bme.aut.weatherdemo.datasource.WeatherDetailsNetworkDataSource
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.ui.weather.WeatherViewState
import hu.bme.aut.weatherdemo.util.NetworkResponse
import hu.bme.aut.weatherdemo.util.NetworkResult

class WeatherDetailsRepository {
   suspend fun getWeatherDetails(cityName: String) : NetworkResponse<Any> {
        return WeatherDetailsNetworkDataSource.getWeatherDetails(cityName)
    }
}