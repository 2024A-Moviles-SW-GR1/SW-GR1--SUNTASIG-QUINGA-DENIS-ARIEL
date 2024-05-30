package entities

import java.io.*
import java.time.LocalDate

data class Estudiante(
    var id: Int,
    var nombre: String,
    var fechaNacimiento: LocalDate,
    var grado: Int,
    var promedio: Double
) : Serializable {
    companion object {
        private const val FILE_NAME = "src/main/kotlin/files/estudiantes.txt"

        // Guarda la lista de estudiantes en un archivo
        fun guardarEstudiantes(estudiantes: List<Estudiante>) {
            try {
                ObjectOutputStream(FileOutputStream(FILE_NAME)).use { it.writeObject(estudiantes) }
            } catch (e: IOException) {
                println("Error al guardar los estudiantes: ${e.message}")
            }
        }

        // Carga la lista de estudiantes desde un archivo
        fun cargarEstudiantes(): MutableList<Estudiante> {
            val file = File(FILE_NAME)
            return if (file.exists() && file.length() > 0) {
                try {
                    ObjectInputStream(FileInputStream(FILE_NAME)).use { it.readObject() as MutableList<Estudiante> }
                } catch (e: IOException) {
                    println("Error al cargar los estudiantes: ${e.message}")
                    mutableListOf()
                }
            } else {
                mutableListOf()
            }
        }

        // Crea un nuevo estudiante y lo agrega a la lista de estudiantes
        fun crearEstudiante(estudiantes: MutableList<Estudiante>, estudiante: Estudiante) {
            if (estudiante.nombre.isNotBlank() && estudiante.grado > 0 && estudiante.promedio >= 0) {
                estudiantes.add(estudiante)
                guardarEstudiantes(estudiantes)
            } else {
                println("Nombre no puede estar vacío, grado debe ser mayor que 0 y promedio no puede ser negativo.")
            }
        }

        // Actualiza un estudiante existente en la lista de estudiantes
        fun actualizarEstudiante(estudiantes: MutableList<Estudiante>, id: Int, nuevoEstudiante: Estudiante) {
            val estudiante = estudiantes.find { it.id == id }
            if (estudiante != null) {
                if (nuevoEstudiante.nombre.isNotBlank() && nuevoEstudiante.grado > 0 && nuevoEstudiante.promedio >= 0) {
                    estudiante.nombre = nuevoEstudiante.nombre
                    estudiante.fechaNacimiento = nuevoEstudiante.fechaNacimiento
                    estudiante.grado = nuevoEstudiante.grado
                    estudiante.promedio = nuevoEstudiante.promedio
                    guardarEstudiantes(estudiantes)
                } else {
                    println("Nombre no puede estar vacío, grado debe ser mayor que 0 y promedio no puede ser negativo.")
                }
            } else {
                println("Error: No se encontró un estudiante con el ID proporcionado.")
            }
        }

        // Elimina un estudiante de la lista de estudiantes
        fun eliminarEstudiante(estudiantes: MutableList<Estudiante>, id: Int) {
            if (estudiantes.removeIf { it.id == id }) {
                guardarEstudiantes(estudiantes)
            } else {
                println("Error: No se encontró un estudiante con el ID proporcionado.")
            }
        }

        // Elimina un estudiante de la lista de estudiantes por ID
        fun eliminarEstudiantePorId(id: Int) {
            val estudiantes = cargarEstudiantes()
            estudiantes.removeIf { it.id == id }
            guardarEstudiantes(estudiantes)
        }

        // Muestra todos los estudiantes en la lista de estudiantes
        fun leerEstudiantes(estudiantes: List<Estudiante>) {
            if (estudiantes.isEmpty()) {
                println("No hay estudiantes para mostrar.")
            } else {
                estudiantes.forEach { estudiante ->
                    println("Estudiante ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Fecha de Nacimiento: ${estudiante.fechaNacimiento}, Grado: ${estudiante.grado}, Promedio: ${estudiante.promedio}")
                }
            }
        }
    }
}