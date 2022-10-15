package com.codejunior.rickandmorty

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codejunior.rickandmorty.databinding.ItemCharacterBinding
import com.codejunior.rickandmorty.retrofit.Character

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCharacterBinding.bind(view)

    fun render(character: Character, onClickListener: (Character) -> Unit) {
        binding.characterSpecie.text = character.species
        binding.characterName.text = character.name
        binding.imgCharacter.setOnClickListener { onClickListener(character) }
        Glide.with(binding.imgCharacter.context).load(character.image).into(binding.imgCharacter)
    }

}