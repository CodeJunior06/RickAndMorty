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

    fun cicloConsumerAll() {

        viewModelScope.launch {
            for (i in 1..42) {
                response = mainModel.getResponseDinamic(getStringToInt(response!!.info.next))
                listCharacter.value = response!!
            }
        }

    }

    fun getStringToInt(nextOrPrev: String?): Int {
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

        runCatching {
            viewModelScope.launch {
                for (i in character) {
                    mainModel.initInsert(i)
                }
            }

        }

    }

    fun getCharacter(): Deferred<Unit> {

        return viewModelScope.async {
            mainModel.get().listIterator().forEach {
                println("GET TABLE CHARACTER : " + it.name + " " + it.id)
            }
        }
    }

    fun getCharacter2(): Deferred<List<CharacterEntity>> {

        return viewModelScope.async {
            mainModel.get()
        }
    }

}

