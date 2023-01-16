package com.codejunior.rickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.model.IBaseModel

class CharacterAdapter(
    private val listCharacter: List<IBaseModel>,
    private val onClickListener: (IBaseModel) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(layoutInflater.inflate(R.layout.item_character, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        holder.render(listCharacter[position], onClickListener)
    }

    override fun getItemCount(): Int = listCharacter.size
}