package com.example.trabalho3unidade.ui.login.repository

import com.example.trabalho3unidade.ui.login.model.Cidade
import com.example.trabalho3unidade.ui.login.model.Estado

interface CidadeRepository {
    fun save(cidade: Cidade)
    fun list(sigla: String): List<Cidade>
}