import java.util.*

fun main() {
    println("Hola mundo")

    // Inmutables (No se pueden re asignan "'="）
    val inmutable: String = "Ariel"
    // inmutable = "Suntasig" // ERROR!
    // Mutables
    var mutable: String = "Ariel"
    mutable = "Suntasig" // 0K
    //val>var

    // Duck Typing
    var ejemploVariable = " Ariel Suntasig "
    val edadEsemplo: Int = 12
    ejemploVariable.trim() // Para borra espacios
    // ejemploVariable = edadEiemala // ERROR!

    // Variables primitivas
    val nombre: String = "Ariel"
    val sueldo: Double = 1.2
    val estadaCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // clases en Java
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        "C" -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //If else chiquito


    // Interpolacion de Strings
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    //Named Parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)


}


//void -> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: $nombre")//String template
}

fun calcularSueldo(
    sueldo: Double, //Requeridos
    tasa: Double = 12.00, //Opcionales (defecto)
    bonoEspecial: Double? = null //Opcionales (pueden ser nulos)
): Double {
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}


abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int, dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }


    abstract class Numeros(
        // Constructor Primar1o
// Caso 1) Parametro normal
// uno:Int , (parametro (sin modificador acceso))

// Caso 2) Parametro y propiedad (atributo) (private)
// private var uno: Int (propiedad "instancia.uno")


        protected val numeroUno: Int, // instancia.numeroUno
        protected val numeroDos: Int, // instancia.numeroDos
        // parametroInutil: String //parametro no se usa
    ) {
        init { // bloque constructor primario
            this.numeroUno
            this.numeroDos
            println("Inicializando")
        }
    }

    class Suma(
        // Constructor primario
        unoParametro: Int, // Parametro
        dosParametro: Int, // Parametros
    ) : Numeros( // Clase papa, Numeros (extendiendo)
        unoParametro,

        dosParametro
    ) {
        public val soyPublicoExplicito: String = "Explicito" // Publicas
        val soyPublicoImplicito: String = "Implicito" // Publicas (propiedades, metodos)

        init { // Bloque Codigo Constructor primario
            this.numeroUno

            this.numeroDos

            numeroUno // this. OPCIONAL (propiedades, metodos)
            numeroDos // this. OPCIONAL (propiedades, metodos)
            this.soyPublicoExplicito
            soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
        }
    }

    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar(): Int {
        val total = numeroUno + numeroDos
// Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)

        return total

    }

    companion object { // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num


        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)

        }
    }
}