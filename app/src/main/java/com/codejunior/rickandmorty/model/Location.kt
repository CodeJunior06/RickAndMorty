package com.codejunior.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name") private val name: String,
    @SerializedName("url") private val url: String,
)
