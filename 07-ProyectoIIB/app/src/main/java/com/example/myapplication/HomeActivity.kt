package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Configurar RecyclerView
        val consejosRecyclerView = findViewById<RecyclerView>(R.id.consejosRecyclerView)
        consejosRecyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de consejos simulada
        val consejosList = listOf(
            Consejo("Cuidado Personal", "Higiene Regular", "Baño regular a tu mascota..."),
            Consejo("Cuidado Personal", "Higiene Regular", "Limpieza de oídos..."),
            Consejo("Confort", "Espacio Propio", "Dale un espacio cómodo a tu mascota..."),
            Consejo("Alimentación", "Dieta Balanceada", "Proporciona una dieta balanceada y adecuada para su especie..."),
            Consejo("Ejercicio", "Actividad Física", "Asegúrate de que tu mascota haga ejercicio diariamente..."),
            Consejo("Salud", "Visitas al Veterinario", "Lleva a tu mascota al veterinario al menos una vez al año..."),
            Consejo("Cuidado Dental", "Limpieza de Dientes", "Cepilla los dientes de tu mascota regularmente para evitar problemas dentales..."),
            Consejo("Hidratación", "Agua Fresca", "Asegúrate de que tu mascota tenga acceso a agua fresca en todo momento..."),
            Consejo("Entrenamiento", "Comandos Básicos", "Enseña a tu mascota comandos básicos como sentarse, quedarse y venir..."),
            Consejo("Seguridad", "Collar con Identificación", "Ponle un collar con identificación a tu mascota en caso de que se pierda..."),
            Consejo("Juguetes", "Estimulación Mental", "Proporciona juguetes que estimulen mentalmente a tu mascota..."),
            Consejo("Socialización", "Interacción con Otros", "Deja que tu mascota interactúe con otras mascotas de manera segura..."),
            Consejo("Paseos", "Salidas Diarias", "Saca a pasear a tu mascota diariamente para que explore y socialice..."),
            Consejo("Vacunas", "Inmunización", "Mantén al día las vacunas de tu mascota según lo recomendado por el veterinario..."),
            Consejo("Desparasitación", "Control de Parásitos", "Realiza desparasitaciones regulares para proteger a tu mascota de parásitos..."),
            Consejo("Cuidado del Pelo", "Cepillado Regular", "Cepilla el pelo de tu mascota regularmente para evitar enredos y controlar la muda..."),
            Consejo("Ambiente", "Espacio Limpio", "Mantén limpio el espacio donde vive tu mascota para prevenir enfermedades..."),
            Consejo("Atención", "Tiempo de Calidad", "Dedica tiempo de calidad a tu mascota cada día..."),
            Consejo("Prevención", "Protección contra Pulgas y Garrapatas", "Usa productos para proteger a tu mascota de pulgas y garrapatas..."),
            Consejo("Cuidado Especial", "Mascotas Mayores", "Dale un cuidado especial a las mascotas mayores con chequeos más frecuentes..."),
            Consejo("Calma", "Ambiente Tranquilo", "Proporciona un ambiente tranquilo para que tu mascota pueda descansar..."),
            Consejo("Educación", "Refuerzo Positivo", "Utiliza refuerzos positivos al educar a tu mascota para fortalecer el buen comportamiento...")
        )

        // Configurar adaptador
        val consejoAdapter = ConsejoAdapter(consejosList)
        consejosRecyclerView.adapter = consejoAdapter

        // Configurar BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_settings // Establecer el ítem seleccionado

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Navegar a la pantalla de inicio (Home)
                    startActivity(Intent(this, GuiaEntrenamiento::class.java))
                    true
                }
                R.id.nav_favorites -> {
                    // Navegar a la pantalla de favoritos
                    startActivity(Intent(this, Progreso_mascota::class.java))
                    true
                }
                R.id.nav_settings -> true // Ya estamos en esta actividad
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