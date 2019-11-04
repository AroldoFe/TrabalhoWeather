package com.example.trabalho3unidade.ui.login.retrofit

import com.example.trabalho3unidade.ui.login.model.Cidade
import com.example.trabalho3unidade.ui.login.model.Estado
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBGEService {

    @GET("estados")
    fun allEstados(): Call<List<Estado>>

    @GET("estados/{id}/municipios")
    fun allMunicipios(@Path("id") id: Long): Call<List<Cidade>>
}