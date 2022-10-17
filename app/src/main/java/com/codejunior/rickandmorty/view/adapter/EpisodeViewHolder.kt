package com.codejunior.rickandmorty.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.databinding.ItemEpisodiesBinding

class EpisodeViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemEpisodiesBinding.bind(view)
    fun render(int: Int){
        val episode = "EPISODE $int"
        binding.mameEpisode.text = episode
    }
}