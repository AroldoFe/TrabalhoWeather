package com.example.trabalho3unidade.retrofit

import com.example.trabalho3unidade.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenWeatherMapService {

    @GET("q={cityName},br&appid=${API_KEY}")
    fun getWeatherCity(@Path("id") cityName: String)
}