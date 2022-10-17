package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.domain.retrofit.model.CharacterResponse
import com.codejunior.rickandmorty.domain.retrofit.network.ICharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainModel @Inject constructor(private val retrofit: ICharacterAPI) {

    suspend fun getResponse(): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getAllQuotes(1)
            response.body()
        }
    }

    suspend fun getResponseDinamic(page: Int): CharacterResponse? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getAllQuotes(page)
            response.body()
        }
    }
}