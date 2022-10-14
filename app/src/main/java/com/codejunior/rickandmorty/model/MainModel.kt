package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.retrofit.CharacterResponse
import com.codejunior.rickandmorty.retrofit.network.ICharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainModel @Inject constructor(private val retrofit: ICharacterAPI) {

    suspend fun getResponse() : CharacterResponse?{
       return withContext(Dispatchers.IO){
            val response = retrofit.getAllQuotes(1)
            response.body()
        }
    }
}