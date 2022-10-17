package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codejunior.rickandmorty.model.MainModel
import com.codejunior.rickandmorty.domain.retrofit.model.CharacterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainModel: MainModel) : ViewModel() {

    val listCharacter = MutableLiveData<CharacterResponse>()
    val toastMessage = MutableLiveData<String>()
    val pageChange = MutableLiveData<Int>()
    private var response: CharacterResponse? = null

    fun initConsumer() {
        runCatching {
            runBlocking {
                response = mainModel.getResponse()
            }
        }.onSuccess {
            listCharacter.value = response!!
            return
        }.onFailure {
            response
        }

        if (response == null) {
            toastMessage.value = "ERROR RESPONSE"
            return
        }

    }

    fun cicloConsumer(next: Boolean) {

        runBlocking {
            if (next) {
                response = mainModel.getResponseDinamic(getStringToInt(response!!.info.next))
            } else {
                response = mainModel.getResponseDinamic(getStringToInt(response!!.info.prev))
            }

        }

        if (response == null) {
            toastMessage.value = "ERROR RESPONSE"
            return
        }

        listCharacter.value = response!!
        return

    }

    fun getStringToInt(nextOrPrev: String?): Int {
        if(nextOrPrev ==null){
            return -1
        }
        val list = nextOrPrev.split("=")
        return list[1].toInt()
    }

    fun getPage(page:String?) {
        val response = getStringToInt(page)
        if (response == -1) {
            pageChange.value = 42
            return
        }

        pageChange.value  = response - 1
    }

}