package com.codejunior.rickandmorty.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(entity: CharacterEntity)

    @Query("SELECT * FROM character LIMIT 20")
    suspend fun getCharacter() : List<CharacterEntity>

    @Query("SELECT * FROM character WHERE cha_id BETWEEN :number-19 and :number")
    suspend fun getCharacterForPage(number:Int) : List<CharacterEntity>

    @Query("SELECT * FROM character")
    suspend fun getCharacterAll() : List<CharacterEntity>

    @Query("SELECT COUNT(cha_id) FROM character")
    suspend fun getCountCharacter() : Int
}