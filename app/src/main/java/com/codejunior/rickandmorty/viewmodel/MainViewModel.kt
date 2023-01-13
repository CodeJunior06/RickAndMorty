package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.model.MainModel
import com.codejunior.rickandmorty.domain.retrofit.model.character.CharacterResponse
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.view.utilities.Defines.Companion.ERROR_INTERNET
import com.codejunior.rickandmorty.view.utilities.Defines.Companion.ERROR_INTERNET_AND_DB
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

    @Inject
    lateinit var utils: Utils
    private var response: CharacterResponse? = null

    fun initConsumer(consumerAll: Boolean) {

        viewModelScope.launch {

            var modelResponse = async { mainModel.getResponse() }

            response = modelResponse.await()

            if (response == null || response!!.results.isEmpty()) {
                toastMessage.value = ERROR_INTERNET
                return@launch
            }

            //listCharacter.value = response!!
            insertDataBase(response!!.results)

            if (consumerAll) {
                do {

                    modelResponse =
                        async { mainModel.getResponseDinamic(getStringToInt(response!!.info.next)) }

                    response = modelResponse.await()

                    if (response != null) {
                        //listCharacter.postValue(response!!)
                        insertDataBase(response!!.results)
                    }

                } while (response != null)
                toastMessage.value = "FINISH REQUEST"
            }
        }
    }

    // Consiste en traer la respuesta por pagina si existe internet
    fun cycleConsumer(next: Boolean) {
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
                toastMessage.value = "RESPONSE ERROR TO REQUEST"
                return
            }
        }

    }

    fun cycleConsumer(next: Boolean, pageCurrent: String) {
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

    private var increment: Int = 0
    private fun insertDataBase(character: List<Character>) {

        viewModelScope.launch(Dispatchers.Default) {
            for (i in character) {
                mainModel.initInsert(i)
                println("Personage: $increment ${i.name}")
                increment++
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

    fun init() {
        viewModelScope.launch {
            if (!utils.getServiceInternet()) {
                if(!validExistCharacter()){
                    delay(1000)
                    toastMessage.value = ERROR_INTERNET_AND_DB
                    return@launch
                }
                delay(2000)
                toastMessage.value = ERROR_INTERNET;
                return@launch
            }
            initConsumer(true)
        }

    }

    private suspend fun validExistCharacter(): Boolean {
        return mainModel.getExistCharacter() >= 825
    }
}


