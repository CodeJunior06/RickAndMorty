package com.codejunior.rickandmorty.domain.retrofit.model.character

import android.os.Parcelable
import com.codejunior.rickandmorty.model.IBaseModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @SerializedName("id")  val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type")  val type: String,
    @SerializedName("gender")  val gender: String,
    @SerializedName("origin")  val origin: Origin,
    @SerializedName("location")  val location: Location,
    @SerializedName("episode")  val episode: List<String>,
    @SerializedName("image")  val image: String,
    @SerializedName("url")  val url: String,
    @SerializedName("created")  val created: String,
) : Parcelable,IBaseModel
