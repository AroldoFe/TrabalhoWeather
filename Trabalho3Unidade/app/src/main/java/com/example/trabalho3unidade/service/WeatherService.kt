package com.example.trabalho3unidade.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Clima
import com.example.trabalho3unidade.notificacao.NotificacaoUtils
import com.example.trabalho3unidade.retrofit.RetrofitInicializer

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherService : Service(){

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var cidade : Cidade = intent?.getSerializableExtra("cidade") as Cidade
        buscarClimaCidade(cidade)

        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun buscarClimaCidade(cidade: Cidade){
        var call: Call<Clima> = RetrofitInicializer().openWeatherService().getWeatherByCity(cidade.nome)
        call.enqueue(object: Callback<Clima> {
            override fun onResponse(call: Call<Clima>, response: Response<Clima>) {
                var clima: Clima = response?.body() ?: Clima(response.body()!!.name, response.body()!!.temp, response.body()!!.temp_min, response.body()!!.temp_max)
                lancarNotificacao(clima, cidade)
            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                lancarNotificacao(null, cidade)
            }
        })

//        stopSelf()
    }

    private fun lancarNotificacao(clima: Clima?, cidade: Cidade) {
        if(clima != null){
            NotificacaoUtils.notificationSimple(applicationContext, clima, cidade)
        } else {
            NotificacaoUtils.notificacaoError(applicationContext, cidade)
        }
    }
}
