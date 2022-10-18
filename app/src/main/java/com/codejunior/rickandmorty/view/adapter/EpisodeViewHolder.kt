package com.codejunior.rickandmorty.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.databinding.ItemEpisodiesBinding
import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode

class EpisodeViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemEpisodiesBinding.bind(view)
    fun render(episode: Episode){
/*        val episode = "EPISODE $int"
        binding.mameEpisode.text = episode*/
        binding.episode.text = episode.episode
        binding.mameEpisode.text = episode.name
        binding.presentEpisode.text = episode.created
    }
}