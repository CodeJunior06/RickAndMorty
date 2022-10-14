package com.codejunior.rickandmorty.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.codejunior.rickandmorty.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashView : AppCompatActivity() {
    private lateinit var _binding: ActivitySplashBinding
    private val bindingSplash get() = _binding
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplash.root)
        context = this
        startTimer()
    }

    private fun startTimer() {
        object : CountDownTimer(2500, 1500) {
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