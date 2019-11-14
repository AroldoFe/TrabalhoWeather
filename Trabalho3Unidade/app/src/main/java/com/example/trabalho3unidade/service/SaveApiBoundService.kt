package com.example.trabalho3unidade.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.retrofit.RetrofitInicializer
import com.example.trabalho3unidade.utils.LOG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveApiBoundService : Service() {

    lateinit var repository: SQLiteRepository

    fun getEstadosCidades() {
        this.repository = SQLiteRepository(applicationContext)
        if(repository.list().isNullOrEmpty()){
            this.getEstados()
        } else{
            this.getCidades()
        }

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getEstadosCidades()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun getEstados(){
        val call: Call<List<Estado>> = RetrofitInicializer().ibgeService().allEstados()

        call.enqueue(object : Callback<List<Estado>> {
            override fun onResponse(call: Call<List<Estado>>, response: Response<List<Estado>>) {
                var estados : List<Estado> = response?.body() ?: ArrayList<Estado>()
                Log.d(LOG,"\tAPI IBGE: Salvando os Estados")
                for(estado in estados){
                    repository.save(estado)
                }

                getCidades()
            }

            override fun onFailure(call: Call<List<Estado>>, t: Throwable) {
                exibirErro("Erro ao recuperar os estados")
            }
        })
    }

    private fun getCidades(){
        /**
         * Recuperando os Estados
         */
        val estados: List<Estado> = repository.list()

        for(estado in estados){
            var call: Call<List<Cidade>> = RetrofitInicializer().ibgeService().allMunicipios(estado.id)

            call.enqueue(object: Callback<List<Cidade>>{
                override fun onResponse(call: Call<List<Cidade>>, response: Response<List<Cidade>>) {
                    var cidades: List<Cidade> = response?.body() ?: ArrayList<Cidade>()
                    Log.d(LOG,"\tAPI IBGE: Salvando as Cidades do ${estado.sigla} : ${cidades[0].toString()}")
                    for(cidade in cidades){
                        cidade.UF = estado
                        repository.save(cidade)
                    }
                    if (estado == estados.get(estados.size-1)){
                        stopSelf()
                    }
                }

                override fun onFailure(call: Call<List<Cidade>>, t: Throwable) {
                    exibirErro("Erro ao recuperar uma cidade")
                }
            })
        }
    }

    private fun exibirErro(message: String){
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
