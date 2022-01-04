package hu.bme.aut.weatherdemo.ui.weather

import hu.bme.aut.weatherdemo.model.network.WeatherResult

sealed class WeatherViewState

//1: egy singleton, aminek 1 db értéke van
object InProgress : WeatherViewState()

//2: ha az állapot WeatherResponseSuccess, akkor elkérem a WeatherResult data-ját
data class WeatherResponseSuccess(
    val data: WeatherResult
) : WeatherViewState()

//3: ha az állapot WeatherResponseError, akkor elkérem az error message-t
data class WeatherResponseError(
    val exceptionMsg: String
) : WeatherViewState()