package com.codejunior.rickandmorty.retrofit.network

import com.codejunior.rickandmorty.retrofit.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ICharacterAPI {
    @GET("character/")
    suspend fun getAllQuotes(@Query("page") numberPage:Int): Response<CharacterResponse>
}