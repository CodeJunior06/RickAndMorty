package com.codejunior.rickandmorty.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.codejunior.rickandmorty.CharacterAdapter
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.databinding.FragmentCharacterBinding
import com.codejunior.rickandmorty.extension.toastMessage
import com.codejunior.rickandmorty.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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
            bindingMain.recyclerCharacter.layoutManager = GridLayoutManager(requireContext(), 2)
            bindingMain.recyclerCharacter.adapter =
                CharacterAdapter(it.results) { model ->
                    toastMessage(model.name)
                    findNavController().navigate(R.id.action_characterFragment_to_informationFragment)
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
    }

}