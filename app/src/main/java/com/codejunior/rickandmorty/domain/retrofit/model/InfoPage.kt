package com.codejunior.rickandmorty.domain.retrofit.model

import com.google.gson.annotations.SerializedName

data class InfoPage(
    @SerializedName("count") private val count: Int,
    @SerializedName("pages") private val pages: Int,
    @SerializedName("next") val next: String,
    @SerializedName("prev") val prev: String
)
