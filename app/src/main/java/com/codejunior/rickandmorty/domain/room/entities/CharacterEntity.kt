package com.codejunior.rickandmorty.domain.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "cha_id") val id: Int,
    @ColumnInfo(name = "cha_name") val name: String,
    @ColumnInfo(name = "cha_status") val status: String,
    @ColumnInfo(name = "cha_specie") val species: String,
    @ColumnInfo(name = "cha_type") val type: String,
    @ColumnInfo(name = "cha_gender") val gender: String,
    @ColumnInfo(name = "cha_origin") val origin: String,
    @ColumnInfo(name = "cha_location") val location: String,
    @ColumnInfo(name = "cha_episode") val episode: Int,
    @ColumnInfo(name = "cha_image") val image: String,
    @ColumnInfo(name = "cha_url") val url: String,
    @ColumnInfo(name = "cha_created") val created: String,
)

fun Character.render(character: Character) = CharacterEntity(
    character.id,
    character.name,
    character.status,
    character.species,
    character.type,
    character.gender,
    character.origin.name,
    character.location.name,
    character.episode.size,
    character.image,
    character.url,
    character.created
)