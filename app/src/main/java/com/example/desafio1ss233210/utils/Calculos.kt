package com.example.desafio1ss233210.utils

object Calculos {

    // Ejercicio 1
    fun calcularPromedio(n1: Double, n2: Double, n3: Double, n4: Double, n5: Double): Double {
        return (n1 * 0.25) + (n2 * 0.25) + (n3 * 0.20) + (n4 * 0.15) + (n5 * 0.15)
    }

    fun estaAprobado(promedio: Double): Boolean {
        return promedio >= 6.0
    }

    fun notaEnRango(nota: Double): Boolean {
        return nota in 0.0..10.0
    }


    // Ejercicio 2
    fun calcularAfp(salario: Double): Double {
        return salario * 0.0725
    }

    fun calcularIsss(salario: Double): Double {
        return salario * 0.03
    }

    // Recibe la RENTA IMPONIBLE (salario ya menos AFP e ISSS), no el salario bruto
    fun calcularRenta(rentaImponible: Double): Double {
        return when {
            rentaImponible <= 472.00 -> 0.0
            rentaImponible <= 895.24 -> ((rentaImponible - 472.00) * 0.10) + 17.67
            rentaImponible <= 2038.10 -> ((rentaImponible - 895.24) * 0.20) + 60.00
            else -> ((rentaImponible - 2038.10) * 0.30) + 288.57
        }
    }

    fun calcularSalarioNeto(salarioBruto: Double, renta: Double, afp: Double, isss: Double): Double {
        return salarioBruto - renta - afp - isss
    }

// Ejercicio 3
fun sumar(a: Double, b: Double): Double = a + b
fun restar(a: Double, b: Double): Double = a - b
fun multiplicar(a: Double, b: Double): Double = a * b

fun dividir(a: Double, b: Double): Double? {
    if (b == 0.0) return null
    return a / b
}

fun potencia(base: Double, exponente: Double): Double = Math.pow(base, exponente)

fun raizCuadrada(a: Double): Double? {
    if (a < 0) return null
    return Math.sqrt(a)
}
}