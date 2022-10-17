package com.codejunior.rickandmorty.domain.retrofit.model.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info: InfoPage,
    @SerializedName("results") var results: ArrayList<Character>
)
