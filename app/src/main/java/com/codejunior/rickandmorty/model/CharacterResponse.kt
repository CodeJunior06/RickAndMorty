package com.codejunior.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") private val info: InfoPage,
    @SerializedName("results") private val results: ArrayList<Character>
)
