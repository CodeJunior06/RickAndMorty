package com.codejunior.rickandmorty.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codejunior.rickandmorty.databinding.ActivitySplashBinding
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

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

        if(!viewModelMain.utils.getServiceInternet(this)){
            lifecycleScope.launch(Dispatchers.Main) {
                startTimer()
            }
            return
        }

        viewModelMain.initConsumer()

        viewModelMain.listCharacter.observe(this@SplashView) {
            lifecycleScope.launch { viewModelMain.insertDataBase(it.results) }
        }

       lifecycleScope.launch(Dispatchers.IO) {
            async { viewModelMain.cicloConsumerAll() }.join()
            launch { viewModelMain.getCharacter().await() }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            delay(1800)
            val i = Intent(context, MainView::class.java)
            startActivity(i)
            finish()

        }
    }

    private fun startTimer() {
        object : CountDownTimer(1780, 1500) {
            override fun onTick(p0: Long) {
                println("Timer Splash $p0")
            }

            override fun onFinish() {
                val i = Intent(context, MainView::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
                finish()
            }
        }.start()
    }
}
