package com.example.lesson7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lesson7.models.Hero

class HeroesRecyclerViewAdapter(
    var items: ArrayList<Hero>,
    val onItemClick: (result: Hero) -> Unit
) :
    RecyclerView.Adapter<HeroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val listItemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.hero_item, parent, false)
        return HeroViewHolder(listItemView)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        Glide.with(holder.itemView).load(item.images.md).into(holder.image)
        holder.race.text = item.appearance.race
        holder.itemView.setOnClickListener { onItemClick(items[position]) }
    }
}

class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.hero_image)
    val name: TextView = itemView.findViewById(R.id.name)
    val race: TextView = itemView.findViewById(R.id.race)
}
