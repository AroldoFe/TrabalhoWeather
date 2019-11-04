package com.example.trabalho3unidade.ui.login.repository

import com.example.trabalho3unidade.ui.login.model.Estado

interface EstadoRepository {
    fun save(estado: Estado)
    fun list(): List<Estado>
}