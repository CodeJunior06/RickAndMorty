package com.codejunior.rickandmorty.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codejunior.rickandmorty.databinding.ActivitySplashBinding
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.extension.intentToMainView
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.time.measureTime

@AndroidEntryPoint
class SplashView : AppCompatActivity() {

    private lateinit var _binding: ActivitySplashBinding
    private val bindingSplash get() = _binding

    private val viewModelMain: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplash.root)

        if (!viewModelMain.utils.getServiceInternet()) {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(2000)
                intentToMainView()
            }
            return
        }

        viewModelMain.initConsumer(true)

        viewModelMain.listCharacter.observe(this@SplashView) {
            viewModelMain.insertDataBase(it.results)
        }

        viewModelMain.toastMessage.observe(this@SplashView){
            Log.d("SPLASH",it)
            intentToMainView()
        }
    }
}
