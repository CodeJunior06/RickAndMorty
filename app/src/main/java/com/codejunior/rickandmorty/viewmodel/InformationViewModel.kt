package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codejunior.rickandmorty.domain.retrofit.model.episode.Episode
import com.codejunior.rickandmorty.model.InformationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(private val informationModel: InformationModel) :
    ViewModel() {

    companion object {
        const val ALIVE: String = "Alive"
        const val DEAD: String = "Dead"
    }

    private var rta: String? = null
    val statusPending = MutableLiveData<String>()
    val listEpisode = MutableLiveData<List<Episode>>()
    private  var response =  ArrayList<Episode>()

    fun initConsumerEpisode(lst: List<String>) {
        response.clear()
        runCatching {
            viewModelScope.async {
                for (listModel in lst){ response.add(informationModel.getEpisodeApi(listModel))
                   listEpisode.value = response
                }
            }
        }.onSuccess {
            return
        }.onFailure {
            println("ON FAILURE " + it.message)
            response = emptyList<Episode>() as ArrayList
        }
    }


    fun setStatus(status: String) {
        statusPending.value = status
    }
}