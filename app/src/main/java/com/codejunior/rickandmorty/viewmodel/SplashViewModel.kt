package com.codejunior.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import com.codejunior.rickandmorty.model.SplashModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel  @Inject constructor(private val splashModel: SplashModel): ViewModel() {

    fun initConsumer(){
    }
}