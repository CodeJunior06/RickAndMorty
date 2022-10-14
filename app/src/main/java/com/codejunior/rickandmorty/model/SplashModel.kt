package com.codejunior.rickandmorty.model

import com.codejunior.rickandmorty.retrofit.network.ICharacterAPI
import javax.inject.Inject

class SplashModel @Inject constructor(private val retrofit: ICharacterAPI) {
}