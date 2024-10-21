import kotlin.collections.ArrayDeque

data class Pupusa(val tipo: String, val cantidad: Int) {
    override fun toString(): String {
        return "$cantidad pupusas de $tipo"
    }
}

data class Orden(val cliente: String, val pupusas: List<Pupusa>) {
    override fun toString(): String {
        val detalles = pupusas.joinToString(", ")
        return "$cliente: $detalles"
    }
}

fun main() {
    val sistema = SistemaOrdenes()
    sistema.mostrarMenu()
}

class SistemaOrdenes {
    private val ordenesPendientes: ArrayDeque<Orden> = ArrayDeque() // Cola para las órdenes pendientes
    private val ordenesDespachadas: ArrayDeque<Orden> = ArrayDeque() // Pila para las órdenes despachadas

    fun mostrarMenu() {
        while (true) {
            try {
                println("\nBienvenido a la Pupusería 'El Comalito'")
                println("Seleccione una opción:")
                println("1. Registrar una nueva orden")
                println("2. Ver órdenes pendientes")
                println("3. Despachar orden")
                println("4. Ver órdenes despachadas")
                println("5. Salir")

                when (leerOpcion()) {
                    1 -> registrarOrden()
                    2 -> verOrdenesPendientes()
                    3 -> despacharOrden()
                    4 -> verOrdenesDespachadas()
                    5 -> {
                        println("Saliendo del sistema...")
                        return
                    }
                    else -> println("Opción no válida. Inténtalo de nuevo.")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}. Intente nuevamente.")
            }
        }
    }

    private fun registrarOrden() {
        println("Ingrese el nombre del cliente:")
        val cliente = readln().trim()
        if (cliente.isEmpty()) {
            println("El nombre del cliente no puede estar vacío.")
            return
        }

        println("¿Cuántos tipos de pupusas desea ordenar?")
        val numTipos = leerNumeroPositivo()

        val pupusas = mutableListOf<Pupusa>()
        for (i in 1..numTipos) {
            println("Ingrese el tipo de pupusa #$i:")
            val tipo = readln().trim()
            if (tipo.isEmpty()) {
                println("El tipo de pupusa no puede estar vacío.")
                return
            }
            println("Ingrese la cantidad de pupusas de $tipo:")
            val cantidad = leerNumeroPositivo()
            pupusas.add(Pupusa(tipo, cantidad))
        }

        val nuevaOrden = Orden(cliente, pupusas)
        ordenesPendientes.add(nuevaOrden)
        println("Orden registrada para $cliente: ${nuevaOrden.pupusas.joinToString(", ")}")
    }

    private fun verOrdenesPendientes() {
        if (ordenesPendientes.isEmpty()) {
            println("No hay órdenes pendientes.")
        } else {
            println("Órdenes pendientes:")
            ordenesPendientes.forEachIndexed { index, orden ->
                println("${index + 1}. $orden")
            }
        }
    }

    private fun despacharOrden() {
        if (ordenesPendientes.isEmpty()) {
            println("No hay órdenes para despachar.")
        } else {
            val ordenDespachada = ordenesPendientes.removeFirst()
            ordenesDespachadas.addFirst(ordenDespachada)
            println("Despachando la orden de: ${ordenDespachada.cliente}")
        }
    }

    private fun verOrdenesDespachadas() {
        if (ordenesDespachadas.isEmpty()) {
            println("No hay órdenes despachadas.")
        } else {
            println("Órdenes despachadas:")
            ordenesDespachadas.forEachIndexed { index, orden ->
                println("${index + 1}. $orden")
            }
        }
    }

    private fun leerOpcion(): Int {
        return readln().toIntOrNull() ?: throw IllegalArgumentException("Debe ingresar un número válido.")
    }

    private fun leerNumeroPositivo(): Int {
        while (true) {
            try {
                val numero = readln().toInt()
                if (numero > 0) return numero
                else println("Debe ingresar un número mayor que cero.")
            } catch (e: NumberFormatException) {
                println("Debe ingresar un número válido.")
            }
        }
    }
}
