package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Progreso_mascota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_progreso_mascota)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_favorites // Establecer el ítem seleccionado

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navegar a la pantalla de inicio (Home)
                    startActivity(Intent(this, GuiaEntrenamiento::class.java))
                    true
                }
                R.id.nav_favorites -> true // Ya estamos en esta actividad
                R.id.nav_settings -> {
                    // Navegar a la pantalla de consejos
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Navegar a la pantalla de recordatorios
                    startActivity(Intent(this, ReminderActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}