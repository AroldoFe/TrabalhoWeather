package com.example.trabalho3unidade.ui.login.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.ui.login.adapter.StateAdapter
import com.example.trabalho3unidade.ui.login.model.Estado
import com.example.trabalho3unidade.ui.login.repository.SQLiteRepository
import kotlinx.android.synthetic.main.activity_state.*

class StateActivity : AppCompatActivity() {
    private var estados = mutableListOf<Estado>()
    private var adapter = StateAdapter(estados, this::onStateItemClick)

    companion object{
        lateinit var sqLiteRepository: SQLiteRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state)
        sqLiteRepository = SQLiteRepository(this)
        initRecyclerView()
    }

    fun onStateItemClick(estado: Estado){
        var intent: Intent = Intent(this, CityActivity::class.java)
        intent.putExtra("estado", estado)
        startActivity(intent)
    }

    fun initRecyclerView(){
        estados = sqLiteRepository.list() as MutableList<Estado>
        adapter = StateAdapter(estados, this::onStateItemClick)
        rvEstados.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvEstados.layoutManager = layoutManager
    }

}
