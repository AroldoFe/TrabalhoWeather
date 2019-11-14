package com.example.trabalho3unidade.model

import java.io.Serializable

class Cidade(var id: Long, var nome: String, var UF: Estado) : Serializable{

    override fun toString(): String {
        return "$id | $nome"
    }
}