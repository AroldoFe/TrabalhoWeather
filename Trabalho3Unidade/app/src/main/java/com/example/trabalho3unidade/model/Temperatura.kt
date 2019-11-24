package com.example.trabalho3unidade.model

class Temperatura(var temp: Double, var temp_min: Double, var temp_max: Double) {

    override fun toString(): String {
        return "Temperatura: ${kelvin2celsius(this.temp)} | Máxima: ${kelvin2celsius(this.temp_max)} | Mínima: ${kelvin2celsius(this.temp_min)}"
    }

    fun kelvin2celsius(kel: Double): String{
        return String.format("%.1f", (kel - 273.15))
    }
}