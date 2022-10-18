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

    @Query("SELECT * FROM character")
    suspend fun getCharacter() : List<CharacterEntity>

}