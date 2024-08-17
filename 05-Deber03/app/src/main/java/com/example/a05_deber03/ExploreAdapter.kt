package com.example.a05_deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ExploreAdapter(
    private val exploreItems: List<ExploreItem>
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    class ExploreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val leftImage: ImageView = view.findViewById(R.id.img_left)
        val rightImage: ImageView = view.findViewById(R.id.img_right)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.explore_view, parent, false)
        return ExploreViewHolder(itemView)
    }

    override fun getItemCount(): Int = exploreItems.size

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val item = exploreItems[position]
        holder.leftImage.setImageResource(item.left)
        holder.rightImage.setImageResource(item.right)
    }
}