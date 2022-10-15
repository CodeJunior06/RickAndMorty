package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codejunior.rickandmorty.model.MainModel
import com.codejunior.rickandmorty.retrofit.CharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainModel: MainModel) : ViewModel() {

    val listCharacter = MutableLiveData<CharacterResponse>(null)
    val toastMessage = MutableLiveData("")
    private var response: CharacterResponse? = null

    fun initConsumer() {

        runBlocking {
            response = mainModel.getResponse()
        }

        if (response == null) {
            toastMessage.value = "ERROR RESPONSE"
            return
        }

        listCharacter.value = response!!
        return

    }
    fun cicloConsumer(next:Boolean) {

        runBlocking {
            if(next){
                response = mainModel.getResponseDinamic(getStringtoInt(response!!.info.next))
            }else{
                response = mainModel.getResponseDinamic(getStringtoInt(response!!.info.prev))
            }

        }

        if (response == null) {
            toastMessage.value = "ERROR RESPONSE"
            return
        }

        listCharacter.value = response!!
        return

    }

     fun getStringtoInt(nextOrPrev:String) :Int {
        val list = nextOrPrev.split("=")
        return list[1].toInt()
    }
}