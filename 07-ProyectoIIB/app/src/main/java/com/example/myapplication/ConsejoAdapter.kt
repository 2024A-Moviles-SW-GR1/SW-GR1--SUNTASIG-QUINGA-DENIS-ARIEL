package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConsejoAdapter(private val consejosList: List<Consejo>) :
    RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder>() {

    class ConsejoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        val subtituloTextView: TextView = itemView.findViewById(R.id.subtituloTextView)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        val iconoImageView: ImageView = itemView.findViewById(R.id.iconoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsejoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consejo, parent, false)
        return ConsejoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConsejoViewHolder, position: Int) {
        val consejo = consejosList[position]
        holder.tituloTextView.text = consejo.titulo
        holder.subtituloTextView.text = consejo.subtitulo
        holder.descripcionTextView.text = consejo.descripcion
        // Puedes cambiar el icono basado en la categoría, si lo deseas
    }

    override fun getItemCount() = consejosList.size
}
