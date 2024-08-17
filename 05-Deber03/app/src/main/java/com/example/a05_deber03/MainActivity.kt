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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarCategorias()
        inicializarPlaylists()

        findViewById<ImageView>(R.id.btn_buscar).setOnClickListener {
            irActividad(SearchActivity::class.java)
        }
    }

    private fun inicializarCategorias() {
        val categoriesList = listOf("Playlist", "Álbumes", "Podcast", "Artistas", "Descargados")

        findViewById<RecyclerView>(R.id.rv_playlist_categories).apply {
            adapter = CategoryAdapter(categoriesList)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        }
    }

    private fun inicializarPlaylists() {
        val playlistList = listOf(
            Playlist(R.drawable.megusta, "Tus me gusta", "Playlist · 73 canciones"),
            Playlist(R.drawable.lil_uzi_vert, "Lil uzi Vert", "Artista"),
            Playlist(R.drawable.dj, "DJ", "Selecciona para empezar"),
            Playlist(R.drawable.blessd, "Blessd", "Artista"),
            Playlist(R.drawable.pesopluma, "Peso Pluma", "Artista"),
            Playlist(R.drawable.kendricklamar, "Kendrick Lamar", "Artista"),
            Playlist(R.drawable.carteldesanta, "Cartel De Santa", "Artista"),
            Playlist(R.drawable.aventura, "Aventura", "Artista"),
            Playlist(R.drawable.ozuna, "Ozuna", "Artista"),
            Playlist(R.drawable.myketowers, "Myke Towers", "Artista"),
            Playlist(R.drawable.rauwalejandro, "Rauw Alejandro", "Artista"),
            Playlist(R.drawable.anuelaa, "Anuel AA", "Artista"),
            Playlist(R.drawable.eladiocarrion, "Eladio Carrion", "Artista"),
            Playlist(R.drawable.agregarartistas, "Agregar artistas", "Selecciona para empezar")
        )

        findViewById<RecyclerView>(R.id.rv_playlists).apply {
            adapter = PlaylistAdapter(playlistList)
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        }
    }

    private fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }
}