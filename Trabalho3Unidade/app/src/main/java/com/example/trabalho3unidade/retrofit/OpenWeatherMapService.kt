package com.example.trabalho3unidade.retrofit

import com.example.trabalho3unidade.model.Clima
import com.example.trabalho3unidade.utils.API_KEY

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapService {

    @GET("weather")
    fun getWeatherByCity(@Query("q") cityName: String, @Query("appid") appid: String): Call<Clima>
}