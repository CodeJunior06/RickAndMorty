package com.codejunior.rickandmorty.retrofit

import com.google.gson.annotations.SerializedName

data class InfoPage(
    @SerializedName("count") private val count: Int,
    @SerializedName("pages") private val pages: Int,
    @SerializedName("next") private val next: String,
    @SerializedName("prev") private val prev: String
)
