package com.example.trabalho3unidade.views

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.broadcast.MunicipiosReceiver
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.retrofit.RetrofitInicializer
import com.example.trabalho3unidade.service.SaveApiBoundService
import com.example.trabalho3unidade.utils.LOG
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
        Log.d(LOG,"Main: Iniciando o repositório")
        repository = SQLiteRepository(applicationContext)
        Log.d(LOG,"Main: Tabelas criadas")
        /**
         * Só deve consultar a API do IBGE se o banco estiver vazio
         */
        if(repository.list().isNullOrEmpty()){
            Log.d(LOG,"Main: bindService")
            startService()
        }

        btnListarEstados.setOnClickListener {
            goStateActivity()
        }
    }

    fun startService(){
        intent = Intent(this, SaveApiBoundService::class.java)
        startService(intent)
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
}

