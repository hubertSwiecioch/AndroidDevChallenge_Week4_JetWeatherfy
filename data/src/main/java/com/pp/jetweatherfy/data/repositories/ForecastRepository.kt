/*
 * Copyright 2021 Paulo Pereira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pp.jetweatherfy.data.repositories

import com.pp.jetweatherfy.data.api.OpenWeatherService
import com.pp.jetweatherfy.domain.model.*
import com.pp.jetweatherfy.domain.repositories.forecast.IForecastRepository
import org.joda.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

class ForecastRepository @Inject constructor(private val service: OpenWeatherService) :
    IForecastRepository {

    override suspend fun getForecast(location: Location) =
        service.getWeatherForCity(location.lat, location.lon).let { response ->
            Forecast(
                location.name,
                response.daily.map { day ->
                    DailyForecast(day.dt * 1000,
                        response.hourly.filter { isSameDay(day.dt, it) }
                            .map {
                                HourlyForecast(
                                    it.dt * 1000,
                                    it.temp.roundToInt(),
                                    mapWeather(it.weather[0].main)
                                )
                            },
                        day.temp.day.roundToInt(),
                        day.temp.min.roundToInt(),
                        day.temp.max.roundToInt(),
                        (day.pop * 100).roundToInt(),
                        day.windSpeed.roundToInt(),
                        mapWeather(day.weather[0].main)
                    )
                })
        }


    private fun isSameDay(
        timestamp: Long,
        hourly: com.pp.jetweatherfy.data.model.Forecast.Hourly,
    ): Boolean {
        val currentDay = LocalDateTime(timestamp * 1000)
        val hourlyDay = LocalDateTime(hourly.dt * 1000)

        return currentDay.year == hourlyDay.year &&
                currentDay.monthOfYear() == hourlyDay.monthOfYear() &&
                currentDay.dayOfMonth() == hourlyDay.dayOfMonth()

    }

    private fun mapWeather(weather: String) =
        when(weather) {
            "Snow" -> Weather.Rainy
            "Drizzle" -> Weather.Rainy
            "Rain" -> Weather.Rainy
            "Clear" -> Weather.Sunny
            "Thunderstorm" -> Weather.Thunderstorm
            "Clouds" -> Weather.Cloudy
            "Squall" -> Weather.Windy
            else -> Weather.Sunny
        }

}
