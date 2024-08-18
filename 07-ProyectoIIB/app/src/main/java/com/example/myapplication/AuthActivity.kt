package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class AuthActivity : ComponentActivity() {

    // Usuario y contraseña quemados
    private val hardcodedEmail = "usuario@ejemplo.com"
    private val hardcodedPassword = "contrasena123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Comparar con el usuario quemado
            if (email == hardcodedEmail && password == hardcodedPassword) {
                // Inicio de sesión exitoso con el usuario quemado
                val intent = Intent(this, GuiaEntrenamiento::class.java)
                startActivity(intent)
                finish() // Cierra AuthActivity para que no se pueda volver a ella con el botón "Atrás"
            } else {
                // Manejar errores, por ejemplo, mostrar un mensaje de error
                editTextEmail.error = "Correo o contraseña incorrectos"
            }
        }
    }
}
