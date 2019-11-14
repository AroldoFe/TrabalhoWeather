package com.example.trabalho3unidade.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.model.Estado
import kotlinx.android.synthetic.main.item_state.view.*

class StateAdapter(private val estados: List<Estado>, private val callback: (Estado) -> Unit): RecyclerView.Adapter<StateAdapter.VH>() {

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtEstado: TextView = itemView.txtEstado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_state, parent, false)
        val vh = VH(v)

        vh.itemView.setOnClickListener {
            val estado = estados[vh.adapterPosition]
            callback(estado)
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val estado = estados[position]
        holder.txtEstado.text = estado.nome
    }

    override fun getItemCount(): Int {
        return estados.size
    }

}