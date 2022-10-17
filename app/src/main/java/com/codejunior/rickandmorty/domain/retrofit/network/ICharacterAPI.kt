package com.codejunior.rickandmorty.domain.retrofit.network

import com.codejunior.rickandmorty.domain.retrofit.model.character.CharacterResponse
import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICharacterAPI {
    @GET("character/")
    suspend fun getCharacterPage(@Query("page") numberPage:Int): Response<CharacterResponse>

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") urlEpisode:Int): Response<Episode>
}