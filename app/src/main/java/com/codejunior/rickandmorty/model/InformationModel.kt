package com.codejunior.rickandmorty.model


import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode
import com.codejunior.rickandmorty.domain.retrofit.network.ICharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


class InformationModel @Inject constructor(private val  retrofit: ICharacterAPI){

    suspend fun getEpisodeApi(url:String): Episode{
        return withContext(Dispatchers.IO){
                val curMageia = getEpisode(url)
                val response = retrofit.getEpisode(curMageia)
                response.body()!!
            }
         }
    }

    private fun getEpisode(i:String): Int {
        try {
          return  i.substring(i.length-2,i.length).toInt()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return  i.substring(i.length-1,i.length).toInt()

    }
