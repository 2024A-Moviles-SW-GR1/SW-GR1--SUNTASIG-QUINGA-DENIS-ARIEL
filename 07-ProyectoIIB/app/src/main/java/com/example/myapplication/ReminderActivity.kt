package com.example.myapplication

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class ReminderActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val daysCheckBoxes = listOf(
            findViewById<CheckBox>(R.id.checkbox_sun),
            findViewById<CheckBox>(R.id.checkbox_mon),
            findViewById<CheckBox>(R.id.checkbox_tue),
            findViewById<CheckBox>(R.id.checkbox_wed),
            findViewById<CheckBox>(R.id.checkbox_thu),
            findViewById<CheckBox>(R.id.checkbox_fri),
            findViewById<CheckBox>(R.id.checkbox_sat)
        )

        val reminderButton: Button = findViewById(R.id.reminder_button)
        val timeTextView: TextView = findViewById(R.id.time_textview)

        reminderButton.setOnClickListener {
            val selectedDays = daysCheckBoxes.filter { it.isChecked }.map { it.text.toString() }
            if (selectedDays.isNotEmpty()) {
                Toast.makeText(this, "Recordatorio guardado para: ${selectedDays.joinToString()}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Selecciona al menos un día", Toast.LENGTH_SHORT).show()
            }
        }

        timeTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                timeTextView.text = String.format("%02d:%02d", selectedHour, selectedMinute)
            }, hour, minute, true)
            timePickerDialog.show()
        }

        reminderButton.backgroundTintList = ContextCompat.getColorStateList(this, R.color.yellow)

        // Configurar BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_profile // Establecer el ítem seleccionado

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, GuiaEntrenamiento::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    startActivity(Intent(this, Progreso_mascota::class.java))
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_profile -> true // Ya estamos en esta actividad
                else -> false
            }
        }
    }
}