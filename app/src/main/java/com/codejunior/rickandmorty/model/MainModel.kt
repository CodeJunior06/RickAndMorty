package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.domain.retrofit.model.character.CharacterResponse
import com.codejunior.rickandmorty.domain.retrofit.network.ICharacterAPI
import com.codejunior.rickandmorty.domain.room.DataBaseRoom
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.domain.room.entities.render
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainModel @Inject constructor(
    private val retrofit: ICharacterAPI,
    private val room: DataBaseRoom
) {

    suspend fun getResponse(): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getCharacterPage(1)
            response.body()
        }
    }

    suspend fun getResponseDinamic(page: Int): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getCharacterPage(page)
            response.body()
        }
    }

    suspend fun initInsert(character: List<Character>) {

        withContext(Dispatchers.IO) {
            room.initDataBaseDAO().insertDevice(CharacterEntity(1, "", "", "", "", "", "", "", 20,"","",""))
        }
    }


}
