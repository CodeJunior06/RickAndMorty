package com.codejunior.rickandmorty.retrofit

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id") private val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") private val type: String,
    @SerializedName("gender") private val gender: String,
    @SerializedName("origin") private val origin: Origin,
    @SerializedName("location") private val location: Location,
    @SerializedName("episode") private val episode: List<String>,
    @SerializedName("image")  val image: String,
    @SerializedName("url") private val url: String,
    @SerializedName("created") private val created: String,
)
