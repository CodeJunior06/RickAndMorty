package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.domain.retrofit.model.character.CharacterResponse
import com.codejunior.rickandmorty.domain.retrofit.network.ICharacterAPI
import com.codejunior.rickandmorty.domain.room.DataBaseRoom
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.domain.room.entities.render
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainModel @Inject constructor(
    private val retrofit: ICharacterAPI,
    private val room: DataBaseRoom
) {

    suspend fun getResponse(): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            var response:CharacterResponse? = null
            kotlin.runCatching {
                val responseRetrofit = retrofit.getCharacterPage(1)
                response = responseRetrofit.body()
            }
            response
        }
    }

    suspend fun getResponseDynamic(page: Int): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            if (page == -1) {
                null
            } else {
                val response = retrofit.getCharacterPage(page)
                response.body()
            }
        }
    }

    suspend fun initInsert(character: Character) {

        withContext(Dispatchers.IO) {
            room.initDataBaseDAO().insertDevice(character.render(character))
        }
    }

    suspend fun getCharacterLimit(): List<CharacterEntity> {

        return withContext(Dispatchers.IO) {
            room.initDataBaseDAO().getCharacter()
        }
    }

    suspend fun getCharacterForPage(page: Int): List<CharacterEntity> {
        return withContext(Dispatchers.IO) {
            room.initDataBaseDAO().getCharacterForPage(page * 20)
        }
    }

    suspend fun getCharacterAll(): List<CharacterEntity> {
        return withContext(Dispatchers.IO) {
            room.initDataBaseDAO().getCharacterAll()
        }
    }

    suspend fun getExistCharacter() : Int {
        return withContext(Dispatchers.IO){
            room.initDataBaseDAO().getCountCharacter()
        }
    }
}
