package com.example.trabalho3unidade.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.model.Cidade
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter (private val cidades: List<Cidade>, private val callback: (Cidade) -> Unit): RecyclerView.Adapter<CityAdapter.VH>() {
    class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtCidade: TextView = itemView.txtCidade
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val cidade = cidades[position]
        holder.txtCidade.text = cidade.nome
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        val vh = VH(v)

        vh.itemView.setOnClickListener {
            val cidade = cidades[vh.adapterPosition]
            callback(cidade)
        }
        return vh
    }

    override fun getItemCount(): Int {
        return cidades.size
    }
}