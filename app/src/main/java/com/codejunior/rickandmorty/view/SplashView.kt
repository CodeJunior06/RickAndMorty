package com.codejunior.rickandmorty.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codejunior.rickandmorty.databinding.ActivitySplashBinding
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.concurrent.thread

@AndroidEntryPoint
class SplashView : AppCompatActivity() {
    private lateinit var _binding: ActivitySplashBinding
    private val bindingSplash get() = _binding
    private lateinit var context: Context
    private val viewModelMain: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplash.root)
        context = this

        if (!viewModelMain.utils.getServiceInternet()) {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(2000)
                getIntentMain()
            }
            return
        }
        runBlocking(Dispatchers.Unconfined){
           val job =  async { viewModelMain.initConsumer() }
            if(job.isCompleted){
                getIntentMain()
            }
        }


        viewModelMain.listCharacter.observe(this@SplashView) {
            viewModelMain.insertDataBase(it.results)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getIntentMain() {

        val i = Intent(context, MainView::class.java)
        startActivity(i)
        finish()

    }
}
