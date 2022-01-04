package hu.bme.aut.weatherdemo.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.weatherdemo.model.network.WeatherResult
import hu.bme.aut.weatherdemo.repository.weather.WeatherDetailsRepository
import hu.bme.aut.weatherdemo.util.NetworkErrorResult
import hu.bme.aut.weatherdemo.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherDetailsViewModel : ViewModel() {
   private var weatherRepository: WeatherDetailsRepository = WeatherDetailsRepository()

private val result = MutableLiveData<WeatherViewState>()

fun getWeatherLiveData() = result

fun getWeatherDetails(cityName: String) {
   
    result.value = InProgress
    
    viewModelScope.launch(Dispatchers.IO) {
        val response = weatherRepository.getWeatherDetails(cityName)
        when (response) {
            is NetworkResult -> {
                val weatherResult = response.result as WeatherResult

                result.postValue(WeatherResponseSuccess(weatherResult))
            }
            is NetworkErrorResult -> {
                result.postValue(WeatherResponseError(response.errorMessage.message!!))
            }
        }
    }
}
}