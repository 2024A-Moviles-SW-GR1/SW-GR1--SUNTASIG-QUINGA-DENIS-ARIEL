package entities

import java.io.*
import java.time.LocalDate

data class Estudiante(
    var id: Int,
    var nombre: String,
    var fechaNacimiento: LocalDate,
    var semestre: Int,
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

        // Valida los datos del estudiante
        fun validarEstudiante(estudiante: Estudiante): Boolean {
            return if (estudiante.nombre.isNotBlank() && estudiante.promedio >= 0) {
                true
            } else {
                println("Nombre no puede estar vacío y promedio no puede ser negativo.")
                false
            }
        }

        // Crea un nuevo estudiante y lo agrega a la lista de estudiantes
        fun crearEstudiante(estudiantes: MutableList<Estudiante>, estudiante: Estudiante) {
            if (validarEstudiante(estudiante)) {
                estudiantes.add(estudiante)
                guardarEstudiantes(estudiantes)
            }
        }

        // Actualiza un estudiante existente en la lista de estudiantes
        fun actualizarEstudiante(estudiantes: MutableList<Estudiante>, id: Int, nuevoEstudiante: Estudiante) {
            val estudiante = estudiantes.find { it.id == id }
            if (estudiante != null) {
                if (validarEstudiante(nuevoEstudiante)) {
                    estudiante.nombre = nuevoEstudiante.nombre
                    estudiante.fechaNacimiento = nuevoEstudiante.fechaNacimiento
                    estudiante.semestre = nuevoEstudiante.semestre
                    estudiante.promedio = nuevoEstudiante.promedio
                    guardarEstudiantes(estudiantes)
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
                    println("Estudiante ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Fecha de Nacimiento: ${estudiante.fechaNacimiento}, Semestre: ${estudiante.semestre}, Promedio: ${estudiante.promedio}")
                }
            }
        }
    }
}