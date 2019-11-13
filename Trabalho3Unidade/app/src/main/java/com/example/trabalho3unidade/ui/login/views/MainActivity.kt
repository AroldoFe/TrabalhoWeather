package com.example.trabalho3unidade.ui.login.views

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.ui.login.broadcast.MunicipiosReceiver
import com.example.trabalho3unidade.ui.login.model.Estado
import com.example.trabalho3unidade.ui.login.repository.SQLiteRepository
import com.example.trabalho3unidade.ui.login.retrofit.RetrofitInicializer
import com.example.trabalho3unidade.ui.login.service.SaveApiBoundService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var mySaveApiBoundService: SaveApiBoundService? = null

    companion object{
        lateinit var repository: SQLiteRepository
        lateinit var municipiosReceiver: MunicipiosReceiver
    }

    private val myConnection = object: ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as SaveApiBoundService.MyLocalBinder
            mySaveApiBoundService = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mySaveApiBoundService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SQLiteRepository(this)

        /**
         * Só deve consultar a API do IBGE se o banco estiver vazio
         */
        if(repository.list().isNullOrEmpty()){
            bindService()
        }

        btnListarEstados.setOnClickListener {
            goStateActivity()
        }

    }

    fun bindService(){
        intent = Intent(this, SaveApiBoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
        mySaveApiBoundService?.getEstados()
        Thread{
            Thread.sleep(5000)
            mySaveApiBoundService?.getCidades()
        }
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

