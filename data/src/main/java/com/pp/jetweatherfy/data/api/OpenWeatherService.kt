package com.pp.jetweatherfy.data.api

import com.pp.jetweatherfy.data.BuildConfig
import com.pp.jetweatherfy.data.model.Forecast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("onecall")
    suspend fun getWeatherForCity(
        @Query("lat") city: Double,
        @Query("lon") daysCount: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): Forecast

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        fun create(): OpenWeatherService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherService::class.java)
        }
    }
}