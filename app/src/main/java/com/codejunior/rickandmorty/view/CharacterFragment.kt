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

        viewModel.initConsumer()

        viewModel.listCharacter.observe(viewLifecycleOwner) {
            when (viewModel.getStringToInt(it.info.next)) {
                2 -> {
                    bindingMain.include.linearPrevResponse.visibility = View.GONE
                }
                -1 -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.GONE
                }
                else -> {
                    bindingMain.include.linearPrevResponse.visibility = View.VISIBLE
                    bindingMain.include.linearAfterResponse.visibility = View.VISIBLE
                }

            }
            viewModel.getPage(it.info.next)

            bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(requireContext(), 2)
            bindingMain.recyclerCharacter.adapter =
                CharacterAdapter(it!!.results) { model ->
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

        viewModel.pageChange.observe(viewLifecycleOwner) {
            bindingMain.include.pageChange.text = it!!.toString()
        }
        CoroutineScope(Dispatchers.IO).launch {
           val r = viewModel.getCharacter2().await()
            r.listIterator().forEach {
                println("GET: "+ it.name + " " + it.id)
            }
        }
    }

}

