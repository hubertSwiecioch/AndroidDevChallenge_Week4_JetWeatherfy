package com.pp.jetweatherfy.data.di

import com.pp.jetweatherfy.data.api.OpenWeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOpenWeatherService(): OpenWeatherService = OpenWeatherService.create()
}