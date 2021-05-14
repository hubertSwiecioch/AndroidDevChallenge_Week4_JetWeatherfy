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
package com.pp.jetweatherfy.presentation.forecast.events

import com.pp.jetweatherfy.domain.model.DailyForecast
import com.pp.jetweatherfy.domain.model.Location
import com.pp.jetweatherfy.presentation.forecast.state.ViewStatus
import com.pp.jetweatherfy.presentation.forecast.state.ViewType
import com.pp.jetweatherfy.presentation.forecast.state.WeatherUnit

sealed class ForecastViewEvent {
    data class GetForecast(val location: Location) : ForecastViewEvent()
    data class SetSelectedDailyForecast(val selectedDailyForecast: DailyForecast) : ForecastViewEvent()
    data class SetViewStatus(val viewStatus: ViewStatus) : ForecastViewEvent()
    data class SetViewType(val viewType: ViewType) : ForecastViewEvent()
    data class SetWeatherUnit(val weatherUnit: WeatherUnit) : ForecastViewEvent()
}
