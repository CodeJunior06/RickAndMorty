package com.codejunior.rickandmorty.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codejunior.rickandmorty.databinding.ActivityMainViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainView : AppCompatActivity() {

    private lateinit var _binding: ActivityMainViewBinding
    private val bindingMain get() = _binding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
    }
}