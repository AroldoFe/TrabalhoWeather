package com.example.trabalho3unidade.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Clima
import com.example.trabalho3unidade.model.Temperatura
import com.example.trabalho3unidade.notificacao.NotificacaoUtils
import com.example.trabalho3unidade.retrofit.RetrofitInicializer
import com.example.trabalho3unidade.utils.API_KEY
import com.example.trabalho3unidade.utils.LOG

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
        var call: Call<Clima> = RetrofitInicializer().openWeatherService().getWeatherByCity(cidade.nome, API_KEY)
        call.enqueue(object: Callback<Clima> {
            override fun onResponse(call: Call<Clima>, response: Response<Clima>) {
                if (response?.body() != null){
                    response.body()!!.main.temp
                    var temp = Temperatura(response.body()!!.main.temp, response.body()!!.main.temp_min, response.body()!!.main.temp_max)
                    Log.d(LOG, "NOTIFICAÇÃO: " + response?.body())
                    var clima: Clima = response?.body() ?: Clima(response.body()!!.name, temp)
                    lancarNotificacao(clima, cidade)
                } else{
                    lancarNotificacao(null, cidade)
                }

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
