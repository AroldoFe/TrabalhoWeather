package com.example.trabalho3unidade.repository

import com.example.trabalho3unidade.model.Cidade

interface CidadeRepository {
    fun save(cidade: Cidade)
    fun list(sigla: String): List<Cidade>
}