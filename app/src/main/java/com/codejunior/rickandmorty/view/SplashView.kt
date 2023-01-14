package com.codejunior.rickandmorty.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codejunior.rickandmorty.databinding.ActivitySplashBinding
import com.codejunior.rickandmorty.extension.intentToMainView
import com.codejunior.rickandmorty.extension.toastMessage
import com.codejunior.rickandmorty.view.dialog.InformationDialog
import com.codejunior.rickandmorty.view.utilities.Defines.Companion.ERROR_INTERNET
import com.codejunior.rickandmorty.view.utilities.Defines.Companion.ERROR_INTERNET_AND_DB
import com.codejunior.rickandmorty.view.utilities.Defines.Companion.SUCCESS
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashView : AppCompatActivity() , OnExit{

    private lateinit var _binding: ActivitySplashBinding
    private val bindingSplash get() = _binding

    private val viewModelMain: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bindingSplash.root)

        viewModelMain.init();

        viewModelMain.toastMessage.observe(this@SplashView){
            Log.d("SPLASH",it)

            when(it){
                SUCCESS ->  {
                    intentToMainView()
                    finish()
                }
                ERROR_INTERNET -> {
                    toastMessage(it)
                    intentToMainView()
                    finish()
                }
                ERROR_INTERNET_AND_DB -> {
                    supportFragmentManager.beginTransaction().add(InformationDialog(this),"parent").commit()
                }
            }

        }
    }

    override fun exitApplication() {
        finish()
    }

    override fun tryConnection() {
        viewModelMain.init()
    }
}

interface OnExit {
    fun exitApplication()
    fun tryConnection();
}