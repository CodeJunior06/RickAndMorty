package com.codejunior.rickandmorty.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.codejunior.rickandmorty.CharacterAdapter
import com.codejunior.rickandmorty.databinding.ActivityMainViewBinding
import com.codejunior.rickandmorty.extension.toastMessage
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainView : AppCompatActivity() {

    private lateinit var _binding: ActivityMainViewBinding
    private val bindingMain get() = _binding
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        viewModel.initConsumer()

        viewModel.listCharacter.observe(this) {
            when (viewModel.getStringtoInt(it.info.next)) {
                2 -> {
                    bindingMain.include.linearPrevResponse.visibility = View.GONE
                    bindingMain.include.linearTitle.visibility = View.VISIBLE
                }
                null -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.GONE
                }
                else -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.VISIBLE
                }

            }
            bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(this, 2)
            bindingMain.recyclerCharacter.adapter =
                CharacterAdapter(it.results) { model -> toastMessage(model.name) }
            bindingMain.recyclerCharacter.adapter!!.notifyDataSetChanged()
        }

        viewModel.toastMessage.observe(this) {
            if (it.equals("ERROR RESPONSE")) {
                toastMessage(it.toString())
            }
        }
        bindingMain.include.afterResponse.setOnClickListener {
            viewModel.cicloConsumer(true)
        }
        bindingMain.include.prevResponse.setOnClickListener {
            viewModel.cicloConsumer(false)
        }


    }
}