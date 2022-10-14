package com.codejunior.rickandmorty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codejunior.rickandmorty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
    }
}