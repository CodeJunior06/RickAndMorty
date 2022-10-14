package com.codejunior.rickandmorty.retrofit

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val info: InfoPage,
    @SerializedName("results") val results: ArrayList<Character>
)
