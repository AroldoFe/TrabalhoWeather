package com.example.trabalho3unidade.ui.login.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.ui.login.adapter.CityAdapter
import com.example.trabalho3unidade.ui.login.model.Cidade
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_state.*

class CityActivity : AppCompatActivity() {
    private val cidades = mutableListOf<Cidade>()
    private val adapter = CityAdapter(cidades, this::onCityItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        initRecyclerView()
    }

    fun onCityItemClick(cidade: Cidade){
        var intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra("cidade", cidade)
        startActivity(intent)
    }

    fun initRecyclerView(){
        rvCidades.adapter = adapter
        // Cria as colunas
        val layoutManager = LinearLayoutManager(this)

        TODO("Deixar horizontal")

        rvEstados.layoutManager = layoutManager
    }
}
