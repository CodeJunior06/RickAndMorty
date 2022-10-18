package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.model.MainModel
import com.codejunior.rickandmorty.domain.retrofit.model.character.CharacterResponse
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.view.utilities.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainModel: MainModel) : ViewModel() {

    val listCharacter = MutableLiveData<CharacterResponse>()
    var listCharacterNotConnection = MutableLiveData<List<CharacterEntity>>()
    val toastMessage = MutableLiveData<String>()
    val pageChange = MutableLiveData<Int>()
    private var response: CharacterResponse? = null

    @Inject
    lateinit var utils: Utils

    fun initConsumer() {
        runCatching {
            runBlocking {
                response = mainModel.getResponse()
            }
        }.onSuccess {
            listCharacter.postValue(response!!)
            return
        }.onFailure {
            response
        }

        if (response == null) {
            toastMessage.postValue("CONEXION NO ESTABLECIDA")
            return
        }

    }

    fun cicloConsumer(next: Boolean) {
        runCatching {
            runBlocking {
                if (next) {
                    response = mainModel.getResponseDinamic(getStringToInt(response!!.info.next))
                } else {
                    response = mainModel.getResponseDinamic(getStringToInt(response!!.info.prev))
                }

            }
        }.onSuccess {
            listCharacter.value = response!!
            return
        }.onFailure {
            if (response == null) {
                toastMessage.value = "ERROR RESPONSE"
                return
            }
        }

    }

    fun cicloConsumer(next: Boolean, pageCurrent: String) {
        viewModelScope.launch {

            listCharacterNotConnection.value = if (next) {
                val newCurrentPage = pageCurrent.toInt() + 1
                pageChange.value = newCurrentPage
                mainModel.getCharacterForPage(newCurrentPage)
            } else {
                val oldCurrentPage = pageCurrent.toInt() - 1
                pageChange.value = oldCurrentPage
                mainModel.getCharacterForPage(oldCurrentPage)
            }
        }

    }
    private var provicionalList = ArrayList<Character>()
    fun cicloConsumerAll()  {

        viewModelScope.launch {
            for (i in 1..42) {
                response = mainModel.getResponseDinamic(getStringToInt(response!!.info.next))
                listCharacter.value = response!!
            }
        }
    }

    private fun getStringToInt(nextOrPrev: String?): Int {
        if (nextOrPrev == null) {
            return -1
        }
        val list = nextOrPrev.split("=")
        return list[1].toInt()
    }

    fun getPage(page: String?) {
        val response = getStringToInt(page)
        if (response == -1) {
            pageChange.value = 42
            return
        }

        pageChange.value = response - 1
    }

    fun insertDataBase(character: List<Character>) {

        viewModelScope.launch(Dispatchers.Unconfined) {
            for (i in character) {
                mainModel.initInsert(i)
            }
        }

    }

    fun getCharacterDataBaseLimit(): Deferred<List<CharacterEntity>> {

        return viewModelScope.async {
            mainModel.getCharacterLimit()
        }
    }

    fun getCharacterDataBase(): Deferred<List<CharacterEntity>> {
        return viewModelScope.async {
            mainModel.getCharacterAll()
        }
    }
}


