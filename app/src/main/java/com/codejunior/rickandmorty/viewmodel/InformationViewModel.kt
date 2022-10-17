package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode
import com.codejunior.rickandmorty.model.InformationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(private val informationModel: InformationModel) :
    ViewModel() {

    companion object {
        const val ALIVE: String = "Alive"
        const val DEAD: String = "Dead"
    }

    val statusPending = MutableLiveData<String>()
    val listEpisode = MutableLiveData<List<Episode>>()
    private var response: List<Episode>? = null

    suspend fun initConsumerEpisode(lst: List<String>) {
        runCatching {
          val rta =  viewModelScope.async {
                return@async informationModel.getEpisodeApi(lst as ArrayList)
            }
            response = rta.await()
        }.onSuccess {
            listEpisode.value = response
            return
        }.onFailure {
            println("ON FAILURE "+ it.message)
            response = null
        }
    }


    fun setStatus(status: String) {
        statusPending.value = status
    }
}