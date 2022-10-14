package com.codejunior.rickandmorty.retrofit.network

import com.codejunior.rickandmorty.retrofit.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface ICharacterAPI {
    @GET("")
    suspend fun getAllQuotes(): Response<List<CharacterResponse>>
}