package com.example.trabalho3unidade.retrofit

import com.example.trabalho3unidade.model.Clima
import com.example.trabalho3unidade.utils.API_KEY

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {

    @GET("weather?q={cityName},br&appid=${API_KEY}")
    fun getWeatherByCity(@Query("cityName") cityName: String): Call<Clima>
}