package com.example.extra

class ReciboNomina(
    private val numRecibo: Int,
    private val nombreNomina: String,
    private val puesto: String,
    private val horasTrab: Float,
    private val horasExtraTrab: Float
) {
    private val salarioBase = 200.0

    private fun obtenerMultiplicador(): Double {
        return when (puesto) {
            "Auxiliar" -> 1.20
            "Albañil" -> 1.50
            "Ing. de Obra" -> 2.00
            else -> 1.0
        }
    }

    fun calcularSubtotal(): Double {
        val multiplicador = obtenerMultiplicador()
        val pagoHorasTrab = horasTrab * salarioBase * multiplicador
        val pagoHorasExtra = horasExtraTrab * salarioBase * multiplicador * 2
        return pagoHorasTrab + pagoHorasExtra
    }

    fun generarImpuestos(): Double {
        val subtotal = calcularSubtotal()
        return subtotal * 0.16
    }

    fun calcularTotal(): Double {
        val subtotal = calcularSubtotal()
        val impuestos = generarImpuestos()
        return subtotal - impuestos
    }
}
