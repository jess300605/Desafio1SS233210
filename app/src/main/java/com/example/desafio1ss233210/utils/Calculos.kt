package com.example.desafio1ss233210.utils

object Calculos {

    // Ponderaciones: 25%, 25%, 20%, 15%, 15%
    fun calcularPromedio(n1: Double, n2: Double, n3: Double, n4: Double, n5: Double): Double {
        return (n1 * 0.25) + (n2 * 0.25) + (n3 * 0.20) + (n4 * 0.15) + (n5 * 0.15)
    }

    fun estaAprobado(promedio: Double): Boolean {
        return promedio >= 6.0
    }

    fun notaEnRango(nota: Double): Boolean {
        return nota in 0.0..10.0
    }
}