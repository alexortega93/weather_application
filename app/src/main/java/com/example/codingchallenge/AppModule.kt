package com.example.codingchallenge

import com.example.codingchallenge.data.remote.WeatherDataSource
import com.example.codingchallenge.data.remote.WeatherDataSourceImpl
import com.example.codingchallenge.data.repository.WeatherRepository
import com.example.codingchallenge.data.repository.WeatherRepositoryImpl
import com.example.codingchallenge.network.WeatherService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideCallFactory(httpLoggingInterceptor: HttpLoggingInterceptor): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/"

    @Singleton
    @Provides
    fun provideRetrofit(httpLoggingInterceptor: Call.Factory, moshiConverterFactory: MoshiConverterFactory, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .callFactory(httpLoggingInterceptor)
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(baseUrl)
            .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideWeatherDataSource(weatherService: WeatherService): WeatherDataSource =
        WeatherDataSourceImpl(weatherService)
}

@Module
@InstallIn(ViewModelComponent::class)
object WeatherModule {

    @Provides
    fun provideWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository =
        WeatherRepositoryImpl(weatherDataSource)
}
