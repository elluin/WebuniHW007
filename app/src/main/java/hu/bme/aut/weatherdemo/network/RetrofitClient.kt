package hu.bme.aut.weatherdemo.network

import hu.bme.aut.weatherdemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    const val MainServer = "https://api.openweathermap.org/"

    //by lazy = csak akkor fog példányosodni, ha egyszer valaki már használta
    val retrofitClient: Retrofit.Builder by lazy {
        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okHttpClient = OkHttpClient.Builder()
        //logoló interceptort is adunk hozzá
        okHttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MainServer)
            .client(okHttpClient.build())
            .addConverterFactory(MoshiConverterFactory.create())
    }

    val apiInterface: WeatherAPI by lazy {
        retrofitClient
            .build()
            .create(WeatherAPI::class.java)
    }

}