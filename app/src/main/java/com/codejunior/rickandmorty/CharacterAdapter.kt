package com.codejunior.rickandmorty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.retrofit.Character
import com.codejunior.rickandmorty.retrofit.CharacterResponse

class CharacterAdapter(private val listCharacter:List<Character>) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(layoutInflater.inflate(R.layout.item_character,parent,false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

            holder.render(listCharacter[position])
    }

    override fun getItemCount():Int = listCharacter.size
}