package com.example.trabalho3unidade.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInicializer {
    private val retrofitIBGE: Retrofit
    private val retrofitOpenWeatherMap: Retrofit

    init {
        retrofitIBGE = Retrofit.Builder()
            .baseUrl("https://servicodados.ibge.gov.br/api/v1/localidades/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitOpenWeatherMap = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun ibgeService(): IBGEService {
        return retrofitIBGE.create(IBGEService::class.java)
    }

    fun openWeatherService(): OpenWeatherMapService{
        return retrofitOpenWeatherMap.create(OpenWeatherMapService::class.java)
    }
}