package com.codejunior.rickandmorty.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.view.adapter.CharacterAdapter
import com.codejunior.rickandmorty.databinding.FragmentCharacterBinding
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.extension.toastMessage
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class CharacterFragment : Fragment() {
    private lateinit var _bindingMain: FragmentCharacterBinding
    private val bindingMain get() = _bindingMain

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingMain = FragmentCharacterBinding.inflate(inflater, container, false)
        return bindingMain.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initConsumer(false)

        viewModel.listCharacter.observe(viewLifecycleOwner) {
            viewModel.getPage(it.info.next)

            bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(requireContext(), 2)
            bindingMain.recyclerCharacter.adapter =
                CharacterAdapter(it!!.results) { model ->
                    model as Character
                    toastMessage(model.name)
                    this.arguments?.putParcelable("character", model)
                    findNavController().navigate(
                        R.id.action_characterFragment_to_informationFragment,
                        arguments
                    )
                }
            bindingMain.recyclerCharacter.adapter!!.notifyDataSetChanged()
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) {

            toastMessage(it.toString())
            lifecycleScope.launch(Dispatchers.Main) {
                val response = viewModel.getCharacterDataBaseLimit().await()
                bindingMain.include.pageChange.text = 1.toString()
                if (bindingMain.include.pageChange.text == 1.toString()) {
                    bindingMain.include.linearPrevResponse.visibility = View.GONE
                }
                bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(requireContext(), 2)
                bindingMain.recyclerCharacter.adapter =
                    CharacterAdapter(response) { model ->
                        model as CharacterEntity
                        toastMessage(model.name)
                        arguments?.putParcelable("character", model)
                        findNavController().navigate(
                            R.id.action_characterFragment_to_informationFragment,
                            arguments
                        )
                    }
                bindingMain.recyclerCharacter.adapter!!.notifyDataSetChanged()
            }
        }

        bindingMain.include.afterResponse.setOnClickListener {
            if (!viewModel.utils.getServiceInternet()) {
                viewModel.cycleConsumer(true, bindingMain.include.pageChange.text.toString())
            } else {
                viewModel.cycleConsumer(true)
            }
        }

        bindingMain.include.prevResponse.setOnClickListener {
            if (!viewModel.utils.getServiceInternet()) {
                viewModel.cycleConsumer(false, bindingMain.include.pageChange.text.toString())

            } else {
                viewModel.cycleConsumer(false)
            }

        }

        viewModel.pageChange.observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    bindingMain.include.linearPrevResponse.visibility = View.GONE
                    bindingMain.include.linearAfterResponse.visibility = View.VISIBLE
                }
                42 -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.GONE
                }
                else -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.VISIBLE
                }
            }
            bindingMain.include.pageChange.text = it!!.toString()

        }

        viewModel.listCharacterNotConnection.observe(viewLifecycleOwner) {
            bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(requireContext(), 2)
            bindingMain.recyclerCharacter.adapter =
                CharacterAdapter(it) { model ->
                    model as CharacterEntity
                    toastMessage(model.name)
                    arguments?.putParcelable("character", model)
                    findNavController().navigate(
                        R.id.action_characterFragment_to_informationFragment,
                        arguments
                    )
                }
            bindingMain.recyclerCharacter.adapter!!.notifyDataSetChanged()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val r = viewModel.getCharacterDataBase().await()
            r.listIterator().forEach {
                println("GET: " + it.name + " " + it.id)
            }
        }
    }

}

