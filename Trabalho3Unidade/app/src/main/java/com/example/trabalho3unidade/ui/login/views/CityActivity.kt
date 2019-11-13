package com.example.trabalho3unidade.ui.login.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.ui.login.adapter.CityAdapter
import com.example.trabalho3unidade.ui.login.adapter.StateAdapter
import com.example.trabalho3unidade.ui.login.model.Cidade
import com.example.trabalho3unidade.ui.login.model.Estado
import com.example.trabalho3unidade.ui.login.repository.SQLiteRepository
import kotlinx.android.synthetic.main.activity_city.*
import kotlinx.android.synthetic.main.activity_state.*

class CityActivity : AppCompatActivity() {
    private var cidades = mutableListOf<Cidade>()
    private var adapter = CityAdapter(cidades, this::onCityItemClick)

    companion object{
        lateinit var sqLiteRepository: SQLiteRepository
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        sqLiteRepository = SQLiteRepository(this)
        var estado = intent.getSerializableExtra("estado") as Estado
        initRecyclerView(estado)
    }

    fun onCityItemClick(cidade: Cidade){
        var intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra("cidade", cidade)
        startActivity(intent)
    }

    fun initRecyclerView(estado: Estado){
        cidades = sqLiteRepository.list(estado.sigla) as MutableList<Cidade>
        adapter = CityAdapter(cidades, this::onCityItemClick)
        rvCidades.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCidades.layoutManager = layoutManager
    }
}
