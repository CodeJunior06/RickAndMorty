package com.codejunior.rickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode

class EpisodeAdapter(private val list: List<Episode>) : RecyclerView.Adapter<EpisodeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context)
        return EpisodeViewHolder(view.inflate(R.layout.item_episodies,parent,false))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {

        holder.render(list[position])
       /* holder.render(position+1)*/
    }

    override fun getItemCount(): Int = list.size

}