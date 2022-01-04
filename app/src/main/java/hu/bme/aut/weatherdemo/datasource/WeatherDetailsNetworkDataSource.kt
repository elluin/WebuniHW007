package hu.bme.aut.weatherdemo.datasource

import hu.bme.aut.weatherdemo.BuildConfig
import hu.bme.aut.weatherdemo.network.RetrofitClient
import hu.bme.aut.weatherdemo.util.NetworkErrorResult
import hu.bme.aut.weatherdemo.util.NetworkResponse
import hu.bme.aut.weatherdemo.util.NetworkResult

object WeatherDetailsNetworkDataSource {


    //egy WeatherResult típusú live datát ad vissza
    suspend fun getWeatherDetails(cityName: String): NetworkResponse<Any> {
        try {
            val response = RetrofitClient.apiInterface.getWeatherData(cityName, "metric", BuildConfig.WEATHER_API_KEY)
            response?.let {
                return NetworkResult(it.body()!!)
            }

           //* return NetworkErrorResult(Exception("No data"))
        } catch (e: Exception) {
            return NetworkErrorResult(e)
        }


    }


}