package com.example.trabalho3unidade.ui.login.views

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.ui.login.broadcast.MunicipiosReceiver
import com.example.trabalho3unidade.ui.login.model.Estado
import com.example.trabalho3unidade.ui.login.repository.SQLiteRepository
import com.example.trabalho3unidade.ui.login.retrofit.RetrofitInicializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var repository: SQLiteRepository
        lateinit var municipiosReceiver: MunicipiosReceiver
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SQLiteRepository(this)

        // Mandar buscar os Estados
        buscarEstados()

        btnListarEstados.setOnClickListener {
            goStateActivity()
        }

        // Cria o BroadcastReceiver para procurar os municipios
        municipiosReceiver = MunicipiosReceiver()
        var intentFilter = IntentFilter()
        intentFilter.addAction("com.example.trabalho3unidade.ui.login.broadcastreceiver.BUSCAR_MUNICIPIOS")
        registerReceiver(municipiosReceiver, intentFilter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sair, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.action_sair){
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

    fun goStateActivity(){
        var intent = Intent(this, StateActivity::class.java)
        startActivity(intent)
    }

    private fun exibirErro(t: Throwable){
        Toast.makeText(this, "Erro ao recuperar os Estados!", Toast.LENGTH_SHORT).show()
    }

    private fun buscarEstados(){
        val call: Call<List<Estado>> = RetrofitInicializer().ibgeService().allEstados()

        call.enqueue(object : Callback<List<Estado>>{
            override fun onResponse(call: Call<List<Estado>>, response: Response<List<Estado>>) {
                var estados : List<Estado> = response?.body() ?: ArrayList<Estado>()

                for(estado in estados){
                    repository.save(estado)
                }

                // Lançar BroadcastReceiver para começar a busca das cidades por estado
                sendBuscarMunicipiosBroadcast()
            }

            override fun onFailure(call: Call<List<Estado>>, t: Throwable) {
                exibirErro(t)
            }
        })
    }

    fun sendBuscarMunicipiosBroadcast(){
        var myIntent = Intent("com.example.trabalho3unidade.ui.login.broadcastreceiver.BUSCAR_MUNICIPIOS")
        sendBroadcast(myIntent)
    }
}

