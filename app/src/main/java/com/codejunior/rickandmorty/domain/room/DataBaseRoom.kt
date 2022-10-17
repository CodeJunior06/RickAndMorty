package com.codejunior.rickandmorty.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codejunior.rickandmorty.domain.room.dao.CharacterDao
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity

@Database(entities = [CharacterEntity::class],version = 1,exportSchema = false)
abstract class DataBaseRoom: RoomDatabase() {

    abstract fun instanceDeviceDAO() : CharacterDao
}