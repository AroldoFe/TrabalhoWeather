package com.example.trabalho3unidade.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Clima
import com.example.trabalho3unidade.model.Estado
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.retrofit.RetrofitInicializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MunicipiosReceiver : BroadcastReceiver() {
    lateinit var context: Context


    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        buscarMunicipios()
    }

    private fun buscarMunicipios(){
        var repository = SQLiteRepository(context)

        var estados : List<Estado> = repository.list()

        for(estado in estados){
            var call: Call<List<Cidade>> = RetrofitInicializer().ibgeService().allMunicipios(estado.id)

            call.enqueue(object: Callback<List<Cidade>>{
                override fun onResponse(call: Call<List<Cidade>>,response: Response<List<Cidade>>) {
                    var cidades: List<Cidade> = response?.body() ?: ArrayList<Cidade>()

                    for(cidade in cidades){
                        cidade.UF = estado

                        //repository.save(cidade)

                    }
                }

                override fun onFailure(call: Call<List<Cidade>>, t: Throwable) {
                    Toast.makeText(context, "Erro: Não foi possível carregar alguma cidade!", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun buscarClimaCidade(cidade: Cidade){
        var call: Call<Clima> = RetrofitInicializer().openWeatherService().getWeatherByCity(cidade.nome)
        call.enqueue(object: Callback<Clima>{
            override fun onResponse(call: Call<Clima>,response: Response<Clima>) {
                // var clima: Clima = response?.body() ?: Clima()
            }

            override fun onFailure(call: Call<Clima>, t: Throwable) {
                Toast.makeText(context, "Erro: Não foi possível carregar o clima da cidade!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
