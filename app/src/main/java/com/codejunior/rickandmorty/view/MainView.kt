package com.codejunior.rickandmorty.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.codejunior.rickandmorty.databinding.ActivityMainViewBinding
import com.codejunior.rickandmorty.extension.toastMessage
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainView : AppCompatActivity() {

    private lateinit var _binding: ActivityMainViewBinding
    private val bindingMain get() = _binding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        viewModel.initConsumer()

        viewModel.listCharacter.observe(this, {
            println(it)
        })
        viewModel.toastMessage.observe(this, {

            if (it.equals("ERROR RESPONSE")) {
                toastMessage(it.toString())
            }
        })

    }
}