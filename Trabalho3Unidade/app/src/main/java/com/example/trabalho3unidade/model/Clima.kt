package com.example.trabalho3unidade.model

import java.io.Serializable

class Clima(var name: String, var temp: Double, var temp_min: Double, var temp_max: Double): Serializable{

    override fun toString(): String {
        return "Temperatura: ${kelvin2celsius(temp)} | Mínima: ${kelvin2celsius(temp_min)} | Máxima: ${kelvin2celsius(temp_max)}"
    }

    fun kelvin2celsius(kel: Double): Double{
        return kel - 273.15
    }
}