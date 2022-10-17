package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor() : ViewModel() {

    companion object{
        const val ALIVE:String = "Alive"
        const val DEAD:String = "Dead"
    }

    val statusPending = MutableLiveData<String>()



    fun setStatus(status: String) {
        statusPending.value = status
    }
}