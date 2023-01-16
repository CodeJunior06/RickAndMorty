package com.codejunior.rickandmorty.view.adapter

import android.util.Base64
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.databinding.ItemCharacterBinding
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.model.IBaseModel

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCharacterBinding.bind(view)

    fun render(character: IBaseModel, onClickListener: (IBaseModel) -> Unit) {
        if (character is Character) {
            binding.characterSpecie.text = character.species
            binding.characterName.text = character.name
            Glide.with(binding.imgCharacter.context).load(character.image)
                .placeholder(R.drawable.icon).error(R.drawable.icon).into(binding.imgCharacter)
        } else {
            character as CharacterEntity
            binding.characterSpecie.text = character.species
            binding.characterName.text = character.name
            val imageByteArray: ByteArray = Base64.decode(character.base64, Base64.DEFAULT)
            Glide.with(binding.imgCharacter.context).load(imageByteArray)
                .placeholder(R.drawable.icon).error(R.drawable.icon).into(binding.imgCharacter)
        }
        binding.imgCharacter.setOnClickListener { onClickListener(character) }

    }

}