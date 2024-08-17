package com.example.a05_deber03

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_searchactivity)
        // Configuración para ajustar el padding de la vista
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.buscar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crear los elementos para explorar
        val exploreItems = listOf(
            ExploreItem(R.drawable.creadosparati, R.drawable.nuevoslanzamientos),
            ExploreItem(R.drawable.verano, R.drawable.country),
            ExploreItem(R.drawable.hiphop, R.drawable.musicamexicana),
            ExploreItem(R.drawable.rankingpodcast, R.drawable.podcasts),
            ExploreItem(R.drawable.video, R.drawable.originalesspotify),
            ExploreItem(R.drawable.rankings, R.drawable.pop),
            ExploreItem(R.drawable.latina, R.drawable.rock),
            ExploreItem(R.drawable.descubre, R.drawable.radio),
            ExploreItem(R.drawable.universitario, R.drawable.dance_electronica),
            ExploreItem(R.drawable.enelauto, R.drawable.estadodeanimo)
        )

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rv_explore_items).apply {
            adapter = ExploreAdapter(exploreItems)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        }

        // Configurar el botón para ir a la biblioteca
        findViewById<ImageView>(R.id.btn_library).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}