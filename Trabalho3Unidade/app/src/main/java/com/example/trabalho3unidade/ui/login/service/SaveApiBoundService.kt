package com.example.trabalho3unidade.ui.login.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.example.trabalho3unidade.ui.login.model.Cidade
import com.example.trabalho3unidade.ui.login.model.Estado
import com.example.trabalho3unidade.ui.login.repository.SQLiteRepository
import com.example.trabalho3unidade.ui.login.retrofit.RetrofitInicializer
import com.example.trabalho3unidade.ui.login.views.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveApiBoundService : Service() {

    private val myLocalBinder = MyLocalBinder()
    companion object{
        lateinit var repository: SQLiteRepository
    }

    override fun onCreate() {
        super.onCreate()
        repository = SQLiteRepository(this)
    }

    override fun onBind(intent: Intent): IBinder {
        return myLocalBinder
    }

    public fun getEstados(){
        val call: Call<List<Estado>> = RetrofitInicializer().ibgeService().allEstados()

        call.enqueue(object : Callback<List<Estado>> {
            override fun onResponse(call: Call<List<Estado>>, response: Response<List<Estado>>) {
                var estados : List<Estado> = response?.body() ?: ArrayList<Estado>()

                for(estado in estados){
                    repository.save(estado)
                }
            }

            override fun onFailure(call: Call<List<Estado>>, t: Throwable) {
                exibirErro("Erro ao recuperar os estados")
            }
        })
    }

    public fun getCidades(){
        /**
         * Recuperando os Estados
         */
        val estados: List<Estado> = repository.list()

        for(estado in estados){
            var call: Call<List<Cidade>> = RetrofitInicializer().ibgeService().allMunicipios(estado.id)

            call.enqueue(object: Callback<List<Cidade>>{
                override fun onResponse(call: Call<List<Cidade>>, response: Response<List<Cidade>>) {
                    var cidades: List<Cidade> = response?.body() ?: ArrayList<Cidade>()

                    for(cidade in cidades){
                        cidade.UF = estado
                        repository.save(cidade)
                    }
                }

                override fun onFailure(call: Call<List<Cidade>>, t: Throwable) {
                    exibirErro("Erro ao recuperar uma cidade")
                }
            })
        }
    }

    private fun exibirErro(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    inner class MyLocalBinder: Binder(){
        fun getService(): SaveApiBoundService{
            return this@SaveApiBoundService
        }
    }
}
