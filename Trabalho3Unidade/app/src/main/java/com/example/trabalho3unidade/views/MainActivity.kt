package com.example.trabalho3unidade.views

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.service.SaveApiBoundService

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var repository: SQLiteRepository
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SQLiteRepository(applicationContext)

        startService()

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

