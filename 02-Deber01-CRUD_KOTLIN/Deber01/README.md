# ESCUELA POLITÉCNICA NACIONAL
## Aplicaciones Móviles
#### Estudiante: Ariel Suntasig

#### Profesor: Vicente Eguez

## Deber 1

### Indicaciones

* El objetivo es desarrollar un programa que implemente operaciones CRUD (Crear, Leer, Actualizar, Eliminar). 
Estas operaciones deben aplicarse a dos entidades relacionadas. 
Cada entidad debe tener cinco atributos. 
Por ejemplo, en el caso de una entidad "Nombre", entre los diez atributos de las entidades deben incluirse diferentes tipos de variables, como Fecha, Booleano, String, Entero y Decimal.


* Las entidades están relacionadas en una relación de UNO a MUCHOS. Por ejemplo, en una receta (una entidad), puede contener un arreglo de ingredientes (muchas entidades).


### Solución

Para la solución de este deber se ha desarrollado una aplicación de consola en el lenguaje de programación Kotlin, la cual permite realizar las operaciones CRUD sobre dos entidades interrelacionadas: Clase - Estudiante.

**Estructura del Proyecto:**

1. **Clases Principales:**  
   - Clase (Clase.kt): Representa una clase que contiene varios estudiantes. Incluye atributos como id, nombreGrupo, descripcion, fechaInicio, fechaFin y una lista de estudiantes.
   - Estudiante (Estudiante.kt): Representa un estudiante con atributos como id, nombre, fechaNacimiento, semestre y promedio. 
   - Main (Main.kt): Clase principal que contiene el menú de opciones para realizar las operaciones CRUD. 

2. **Operaciones:**
   - **Crear:** Permite crear una clase y un estudiante.
   - **Leer:** Permite leer una clase y un estudiante.
   - **Actualizar:** Permite actualizar una clase y un estudiante.
   - **Eliminar:** Permite eliminar una clase y un estudiante.
3. **Interfaz de Usuario mediante consola :**
   - **Menú de Opciones:** Se muestra un menú de opciones para seleccionar la operación a realizar.
   - **Ingreso de Datos:** Se solicita el ingreso de datos para crear, leer, actualizar o eliminar una clase o un estudiante.
   - **Mensajes de Confirmación:** Se muestran mensajes de confirmación al realizar una operación CRUD.
   - **Mensajes de Error:** Se muestran mensajes de error al ingresar datos incorrectos o al realizar una operación no válida.

**Menú de Opciones:**

El menú de opciones presenta las siguientes opciones:

   1. Crear una nueva Clase
   2. Registrar un nuevo Estudiante
   3. Visualizar todas las Clases existentes junto con sus estudiantes
   4. Visualizar los Estudiantes de una Clase específica
   5. Visualizar todos los Estudiantes registrados
   6. Actualizar la información de una Clase
   7. Actualizar la información de un Estudiante
   8. Eliminar una Clase existente
   9. Eliminar un Estudiante registrado
   10. Finalizar y salir del Programa
