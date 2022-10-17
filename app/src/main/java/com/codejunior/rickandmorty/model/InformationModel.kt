package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode
import com.codejunior.rickandmorty.domain.retrofit.network.ICharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

import kotlin.collections.ArrayList

class InformationModel @Inject constructor(private val  retrofit: ICharacterAPI){
    suspend fun getEpisodeApi(url:ArrayList<String>): ArrayList<Episode>{
        return withContext(Dispatchers.IO){
            val lstEpisode = ArrayList<Episode>()
            for (i in url){
                val curMageia = getEpisode(i)
                val response = retrofit.getEpisode(curMageia.toInt())
                lstEpisode.add(response.body()!!)
                continue
            }
            lstEpisode
         }

    }

    private fun getEpisode(i:String): String = i.substring(i.length-1,i.length)

}