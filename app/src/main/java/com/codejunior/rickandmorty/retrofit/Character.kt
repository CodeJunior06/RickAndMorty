package com.codejunior.rickandmorty.retrofit

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") private val id: Int,
    @SerializedName("name") private val name: String,
    @SerializedName("status") private val status: String,
    @SerializedName("species") private val species: String,
    @SerializedName("type") private val type: String,
    @SerializedName("gender") private val gender: String,
    @SerializedName("origin") private val origin: Origin,
    @SerializedName("location") private val location: Location,
    @SerializedName("episode") private val episode: List<String>,
    @SerializedName("url") private val url: String,
    @SerializedName("created") private val created: String,
)
