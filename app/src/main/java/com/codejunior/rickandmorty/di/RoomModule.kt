package com.codejunior.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.codejunior.rickandmorty.domain.room.DataBaseRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val NAME_DATA_BASE = "rick_morty_database"


    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DataBaseRoom::class.java,
        NAME_DATA_BASE
    ).build()

    @Singleton
    @Provides
    fun provideCharacterDAO(dataBase: DataBaseRoom) = dataBase.instanceDeviceDAO()

}

