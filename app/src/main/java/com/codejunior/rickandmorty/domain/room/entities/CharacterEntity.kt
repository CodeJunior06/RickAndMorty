package com.codejunior.rickandmorty.domain.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterEntity (
    @PrimaryKey(autoGenerate = false)@ColumnInfo(name ="cha_id")  val id: Int,
    @ColumnInfo(name ="cha_name") val name: String,
    @ColumnInfo(name = "cha_status") val status: String,
    @ColumnInfo(name ="cha_specie") val species: String,
    @ColumnInfo(name="cha_type")  val type: String,
    @ColumnInfo(name="cha_gender")  val gender: String,
    @ColumnInfo(name="cha_origin")  val origin: String,
    @ColumnInfo(name="cha_location")  val location: String,
    @ColumnInfo(name="cha_episode")  val episode: Int,
    @ColumnInfo(name="cha_image")  val image: String,
    @ColumnInfo(name="cha_url")  val url: String,
    @ColumnInfo(name= "cha_created")  val created: String,
)