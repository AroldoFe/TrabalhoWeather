package com.example.trabalho3unidade.repository

import com.example.trabalho3unidade.model.Estado

interface EstadoRepository {
    fun save(estado: Estado)
    fun list(): List<Estado>
}