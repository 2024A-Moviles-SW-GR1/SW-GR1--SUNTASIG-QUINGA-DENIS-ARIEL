import entities.Clase
import entities.Estudiante
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val clases = Clase.cargarClases()

    while (true) {
        mostrarMenu()
        val opcion = getIntInput(scanner, "Por favor, seleccione una opción del menú: ")
        when (opcion) {
            1 -> crearClase(scanner, clases)
            2 -> crearEstudiante(scanner, clases)
            3 -> Clase.leerClases(clases)
            4 -> leerEstudiantesDeClase(scanner, clases)
            5 -> leerTodosLosEstudiantes(clases)
            6 -> actualizarClase(scanner, clases)
            7 -> actualizarEstudiante(scanner, clases)
            8 -> eliminarClase(scanner, clases)
            9 -> eliminarEstudiante(scanner, clases)
            10 -> {
                println("El programa ha finalizado exitosamente. ¡Gracias por usar nuestra aplicación!")
                break
            }

            else -> println("Lo siento, la opción que ingresaste no es válida. Por favor, intenta de nuevo con una opción válida.")
        }
    }
}

fun mostrarMenu() {
    println("Bienvenido al Menú de Opciones CRUD para Clases y Estudiantes")
    println("1. Crear una nueva Clase")
    println("2. Registrar un nuevo Estudiante")
    println("3. Visualizar todas las Clases existentes junto con sus estudiantes")
    println("4. Visualizar los Estudiantes de una Clase específica")
    println("5. Visualizar todos los Estudiantes registrados")
    println("6. Actualizar la información de una Clase")
    println("7. Actualizar la información de un Estudiante")
    println("8. Eliminar una Clase existente")
    println("9. Eliminar un Estudiante registrado")
    println("10. Finalizar y salir del Programa")
}

fun getIntInput(scanner: Scanner, prompt: String): Int {
    println(prompt)
    while (!scanner.hasNextInt()) {
        println("Por favor, ingrese un número válido.")
        scanner.next()
    }
    return scanner.nextInt()
}

fun getClaseById(clases: MutableList<Clase>, id: Int): Clase? {
    return clases.find { it.id == id } ?: println("Clase no encontrada.").let { null }
}

fun getEstudianteById(estudiantes: MutableList<Estudiante>, id: Int): Estudiante? {
    return estudiantes.find { it.id == id } ?: println("Estudiante no encontrado.").let { null }
}

fun crearClase(scanner: Scanner, clases: MutableList<Clase>) {
    val id = getIntInput(scanner, "Ingrese el ID de la Clase:")
    // Comprobar si el ID ya existe
    if (clases.any { it.id == id }) {
        println("Error: Ya existe una clase con el ID proporcionado.")
        return
    }
    scanner.nextLine()
    println("Ingrese el Nombre del grupo de la Clase (Clase de la mañana/Clase de la tarde):")
    val nombre = scanner.nextLine()
    println("Ingrese la Descripción de la Clase:")
    val descripcion = scanner.nextLine()
    val fechaInicio = LocalDate.now()
    val fechaFin = LocalDate.now().plusMonths(10) // Ejemplo de fecha de fin
    val nuevaClase = Clase(id, nombre, descripcion, fechaInicio, fechaFin, mutableListOf())
    Clase.crearClase(clases, nuevaClase)
}

fun crearEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    val claseId = getIntInput(scanner, "Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val clase = getClaseById(clases, claseId)
    if (clase != null) {
        val id = getIntInput(scanner, "Ingrese el ID del Estudiante:")
        // Comprobar si el ID ya existe
        if (clase.estudiantes.any { it.id == id }) {
            println("Error: Ya existe un estudiante con el ID proporcionado.")
            return
        }
        scanner.nextLine()
        println("Ingrese el Nombre del Estudiante:")
        val nombre = scanner.nextLine()
        var fechaNacimiento: LocalDate? = null
        while (fechaNacimiento == null) {
            println("Ingrese la Fecha de Nacimiento del Estudiante (formato AAAA-MM-DD):")
            try {
                fechaNacimiento =
                    LocalDate.parse(scanner.nextLine()) // Convierte la cadena ingresada en un objeto LocalDate
            } catch (e: DateTimeParseException) {
                println("Fecha no válida. Por favor, intente de nuevo.")
            }
        }
        val semestre = getIntInput(scanner, "Ingrese el Semestre del Estudiante:")
        println("Ingrese el Promedio del Estudiante:")
        while (!scanner.hasNextDouble()) {
            println("Por favor, ingrese un número válido.")
            scanner.next()
        }
        val promedio = scanner.nextDouble()
        val nuevoEstudiante = Estudiante(id, nombre, fechaNacimiento, semestre, promedio)
        Estudiante.crearEstudiante(clase.estudiantes, nuevoEstudiante)
        Clase.guardarClases(clases) // Guardar cambios
    } else {
        println("Clase no encontrada.")
    }
}

fun leerEstudiantesDeClase(scanner: Scanner, clases: MutableList<Clase>) {
    val claseId = getIntInput(scanner, "Ingrese el ID de la Clase cuyos estudiantes desea ver:")
    val clase = getClaseById(clases, claseId)
    if (clase != null) {
        Clase.leerEstudiantes(clase.estudiantes)
    }
}

fun leerTodosLosEstudiantes(clases: MutableList<Clase>) {
    if (clases.isEmpty()) {
        println("No hay clases para mostrar.")
    } else {
        clases.forEach { clase ->
            if (clase.estudiantes.isEmpty()) {
                println("\tNo hay estudiantes en esta clase.")
            } else {
                clase.estudiantes.forEach { estudiante ->
                    println("Estudiante ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Fecha de Nacimiento: ${estudiante.fechaNacimiento}, Semestre: ${estudiante.semestre}, Promedio: ${estudiante.promedio}")
                }
            }
        }
    }
}

fun actualizarClase(scanner: Scanner, clases: MutableList<Clase>) {
    val id = getIntInput(scanner, "Ingrese el ID de la Clase a actualizar:")
    // Comprobar si el ID ya existe
    if (clases.none { it.id == id }) {
        println("Error: No existe una clase con el ID proporcionado.")
        return
    }
    scanner.nextLine() // Consume newline
    println("Ingrese el Nuevo Nombre del grupo de la Clase (Clase de la mañana/Clase de la tarde):")
    val nombre = scanner.nextLine()
    println("Ingrese la Nueva Descripción de la Clase:")
    val descripcion = scanner.nextLine()
    val fechaInicio = LocalDate.now()
    val fechaFin = LocalDate.now().plusMonths(6) // Ejemplo de fecha de fin
    val clase = getClaseById(clases, id)
    if (clase != null) {
        if (nombre.isNotBlank() && descripcion.isNotBlank()) {
            clase.nombreGrupo = nombre
            clase.descripcion = descripcion
            clase.fechaInicio = fechaInicio
            clase.fechaFin = fechaFin
            Clase.guardarClases(clases)
        } else {
            println("Nombre de grupo y descripción no pueden estar vacíos.")
        }
    }
}

fun actualizarEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    val claseId = getIntInput(scanner, "Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val clase = getClaseById(clases, claseId)
    if (clase != null) {
        val id = getIntInput(scanner, "Ingrese el ID del Estudiante a actualizar:")
        val estudiante = getEstudianteById(clase.estudiantes, id)
        if (estudiante != null) {
            scanner.nextLine()
            println("Ingrese el Nuevo Nombre del Estudiante:")
            val nombre = scanner.nextLine()
            var fechaNacimiento: LocalDate? = null
            while (fechaNacimiento == null) {
                println("Ingrese la Nueva Fecha de Nacimiento del Estudiante (formato AAAA-MM-DD):")
                try {
                    fechaNacimiento =
                        LocalDate.parse(scanner.nextLine()) // Convierte la cadena ingresada en un objeto LocalDate
                } catch (e: DateTimeParseException) {
                    println("Fecha no válida. Por favor, intente de nuevo.")
                }
            }
            val semestre = getIntInput(scanner, "Ingrese el Nuevo Semestre del Estudiante:")
            println("Ingrese el Nuevo Promedio del Estudiante:")
            while (!scanner.hasNextDouble()) {
                println("Por favor, ingrese un número válido.")
                scanner.next()
            }
            val promedio = scanner.nextDouble()
            if (nombre.isNotBlank() && semestre > 0 && promedio >= 0) {
                estudiante.nombre = nombre
                estudiante.fechaNacimiento = fechaNacimiento
                estudiante.semestre = semestre
                estudiante.promedio = promedio
                Clase.guardarClases(clases) // Guardar cambios
            } else {
                println("Nombre no puede estar vacío, semestre debe ser mayor que 0 y promedio no puede ser negativo.")
            }
        } else {
            println("Estudiante no encontrado.")
        }
    } else {
        println("Clase no encontrada.")
    }
}

fun eliminarClase(scanner: Scanner, clases: MutableList<Clase>) {
    val id = getIntInput(scanner, "Ingrese el ID de la Clase a eliminar:")
    val clase = getClaseById(clases, id)
    if (clase != null) {
        clase.estudiantes.forEach { estudiante ->
            Estudiante.eliminarEstudiantePorId(estudiante.id)
        }
        Clase.eliminarClase(clases, id)
    } else {
        println("Clase no encontrada.")
    }
}

fun eliminarEstudiante(scanner: Scanner, clases: MutableList<Clase>) {
    val claseId = getIntInput(scanner, "Ingrese el ID de la Clase a la que pertenece el Estudiante:")
    val clase = getClaseById(clases, claseId)
    if (clase != null) {
        val id = getIntInput(scanner, "Ingrese el ID del Estudiante a eliminar:")
        Estudiante.eliminarEstudiante(clase.estudiantes, id)
        Clase.guardarClases(clases) // Guardar cambios
    } else {
        println("Clase no encontrada.")
    }



}