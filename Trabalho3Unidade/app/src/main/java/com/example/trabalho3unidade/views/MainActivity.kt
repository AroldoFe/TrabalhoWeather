package com.example.trabalho3unidade.views

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.service.SaveApiBoundService

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private var estados : List<Estado> = mutableListOf<Estado>()
    private var cidades : List<Cidade> = mutableListOf<Cidade>()

    companion object{
        lateinit var repository: SQLiteRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SQLiteRepository(applicationContext)
        estados = repository.list()
        cidades = repository.listCidades()
        startService()

        if(estados.isEmpty()|| cidades.isEmpty()){
            Thread {
                try{
                    Thread.sleep(20000)
                }
                catch (e: Exception){
                    Log.e("Erro","Error no sleep")
                }
                finally {
                    goStateActivity()
                }
            }.start()
        }
        else{
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

