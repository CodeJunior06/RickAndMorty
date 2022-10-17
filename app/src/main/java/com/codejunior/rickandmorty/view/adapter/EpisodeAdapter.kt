package com.codejunior.rickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.R

class EpisodeAdapter(private val list: List<String>) : RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
        return EpisodeViewHolder(view.inflate(R.layout.item_episodies,parent,false))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {

        holder.render(position+1)
    }

    override fun getItemCount(): Int = list.size

}