package com.codejunior.rickandmorty.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDevice(entity: CharacterEntity)

}