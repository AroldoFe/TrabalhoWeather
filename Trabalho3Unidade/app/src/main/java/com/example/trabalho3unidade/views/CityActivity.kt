package com.example.trabalho3unidade.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.adapter.CityAdapter
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Estado
import com.example.trabalho3unidade.repository.SQLiteRepository
import com.example.trabalho3unidade.service.WeatherService

import kotlinx.android.synthetic.main.activity_city.*

class CityActivity : AppCompatActivity() {
    private var cidades = mutableListOf<Cidade>()
    private var adapter = CityAdapter(cidades, this::onCityItemClick)

    companion object{
        lateinit var sqLiteRepository: SQLiteRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        sqLiteRepository = SQLiteRepository(applicationContext)
        var estado = intent.getSerializableExtra("estado") as Estado
        initRecyclerView(estado)
    }

    fun onCityItemClick(cidade: Cidade){
        var intent = Intent(this, WeatherService::class.java)
        intent.putExtra("cidade", cidade)
        startService(intent)
    }

    fun initRecyclerView(estado: Estado){
        cidades = sqLiteRepository.listCidades(estado) as MutableList<Cidade>
        adapter = CityAdapter(cidades, this::onCityItemClick)
        rvCidades.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCidades.layoutManager = layoutManager
    }
}
