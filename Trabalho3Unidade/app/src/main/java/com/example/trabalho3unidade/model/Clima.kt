package com.example.trabalho3unidade.model

import java.io.Serializable

class Clima(var name: String, var main: Temperatura): Serializable{

    override fun toString(): String {
        return main.toString();
    }

    fun kelvin2celsius(kel: Double): Double{
        return (kel - 273.15)
    }
}