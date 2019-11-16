package com.example.trabalho3unidade.retrofit

import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBGEService {

    @GET("estados")
    fun allEstados(): Call<List<Estado>>

    @GET("estados/{id}/municipios")
    fun allMunicipios(@Path("id") id: Long): Call<List<Cidade>>
}