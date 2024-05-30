package entities

import java.io.*
import java.time.LocalDate

data class Clase(
    var id: Int,
    var nombreGrupo: String,
    var descripcion: String,
    var fechaInicio: LocalDate,
    var fechaFin: LocalDate,
    var estudiantes: MutableList<Estudiante>
) : Serializable {
    companion object {
        private const val FILE_NAME = "src/main/kotlin/files/clases.txt"

        // Guarda la lista de clases en un archivo
        fun guardarClases(clases: List<Clase>) {
            try {
                ObjectOutputStream(FileOutputStream(FILE_NAME)).use { it.writeObject(clases) }
            } catch (e: IOException) {
                println("Error al guardar las clases: ${e.message}")
            }
        }

        // Carga la lista de clases desde un archivo
        fun cargarClases(): MutableList<Clase> {
            val file = File(FILE_NAME)
            return if (file.exists() && file.length() > 0) {
                try {
                    ObjectInputStream(FileInputStream(FILE_NAME)).use { it.readObject() as MutableList<Clase> }
                } catch (e: IOException) {
                    println("Error al cargar las clases: ${e.message}")
                    mutableListOf()
                }
            } else {
                mutableListOf()
            }
        }

        // Crea una nueva clase y la agrega a la lista de clases
        fun crearClase(clases: MutableList<Clase>, clase: Clase) {
            if (clase.nombreGrupo.isNotBlank() && clase.descripcion.isNotBlank()) {
                clases.add(clase)
                guardarClases(clases)
            } else {
                println("Nombre de grupo y descripción no pueden estar vacíos.")
            }
        }

        // Actualiza una clase existente en la lista de clases
        fun actualizarClase(clases: MutableList<Clase>, id: Int, nuevaClase: Clase) {
            val clase = clases.find { it.id == id }
            if (clase != null) {
                if (nuevaClase.nombreGrupo.isNotBlank() && nuevaClase.descripcion.isNotBlank()) {
                    clase.nombreGrupo = nuevaClase.nombreGrupo
                    clase.descripcion = nuevaClase.descripcion
                    clase.fechaInicio = nuevaClase.fechaInicio
                    clase.fechaFin = nuevaClase.fechaFin
                    clase.estudiantes = nuevaClase.estudiantes
                    guardarClases(clases)
                } else {
                    println("Nombre de grupo y descripción no pueden estar vacíos.")
                }
            }
        }

        // Elimina una clase de la lista de clases
        fun eliminarClase(clases: MutableList<Clase>, id: Int) {
            clases.removeIf { it.id == id }
            guardarClases(clases)
        }

        // Muestra todas las clases en la lista de clases
        fun leerClases(clases: List<Clase>) {
            clases.forEach { clase ->
                println("Clase ID: ${clase.id}, Nombre: ${clase.nombreGrupo}, Descripción: ${clase.descripcion}, Fecha de Inicio: ${clase.fechaInicio}, Fecha de Fin: ${clase.fechaFin}")
                leerEstudiantes(clase.estudiantes)
            }
        }

        // Muestra todos los estudiantes en una lista de estudiantes
        fun leerEstudiantes(estudiantes: List<Estudiante>) {
            estudiantes.forEach { estudiante ->
                println("\tEstudiante ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Fecha de Nacimiento: ${estudiante.fechaNacimiento}, Semestre: ${estudiante.semestre}, Promedio: ${estudiante.promedio}")
            }
        }
    }
}