package com.codejunior.rickandmorty.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.databinding.FragmentInformationBinding
import com.codejunior.rickandmorty.domain.retrofit.model.character.Character
import com.codejunior.rickandmorty.domain.room.entities.CharacterEntity
import com.codejunior.rickandmorty.model.IBaseModel
import com.codejunior.rickandmorty.view.adapter.EpisodeAdapter
import com.codejunior.rickandmorty.viewmodel.InformationViewModel
import com.codejunior.rickandmorty.viewmodel.InformationViewModel.Companion.ALIVE
import com.codejunior.rickandmorty.viewmodel.InformationViewModel.Companion.DEAD
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Singleton


@AndroidEntryPoint
@Singleton
class InformationFragment : Fragment() {

    private lateinit var bindingInformation: FragmentInformationBinding
    private var bundle: IBaseModel? = null
    private var bundleCharacterEntity: CharacterEntity? = null
    private var bundleCharacter: Character? = null

    private val viewModelInformation: InformationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bundle = requireArguments().getParcelable("character")
        if (bundle is CharacterEntity) {
            bundleCharacterEntity = (bundle as CharacterEntity)
        } else {
            bundleCharacter = (bundle as Character)
            viewModelInformation.initConsumerEpisode(
                bundleCharacter!!.episode
            )
        }
        bindingInformation = FragmentInformationBinding.inflate(inflater, container, false)
        Glide.with(this).load(bundleCharacter?.image ?: bundleCharacterEntity!!.image)
            .into(bindingInformation.imgCharacter)
        return bindingInformation.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelInformation.setStatus(bundleCharacter?.status ?: bundleCharacterEntity!!.status)
        usingView()

        viewModelInformation.listEpisode.observe(viewLifecycleOwner) {
            lifecycleScope.launchWhenResumed {
                bindingInformation.recyclerView.layoutManager = GridLayoutManager(context, 2)
                bindingInformation.recyclerView.adapter = EpisodeAdapter(it)
            }

        }

        viewModelInformation.statusPending.observe(viewLifecycleOwner) {
            when (it) {
                ALIVE -> {
                    bindingInformation.characterStatus.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green_status
                        )
                    )
                }
                DEAD -> {
                    bindingInformation.characterStatus.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red_status
                        )
                    )
                }
                else -> {
                    bindingInformation.characterStatus.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.unknown_status
                        )
                    )
                }
            }
        }
    }

    private fun usingView() {
        bindingInformation.characterName.text =
            bundleCharacter?.name ?: bundleCharacterEntity!!.name
        bindingInformation.characterOrigin.text =
            bundleCharacter?.origin?.name ?: bundleCharacterEntity!!.origin
        bindingInformation.characterLocation.text =
            bundleCharacter?.location?.name ?: bundleCharacterEntity!!.location
        bindingInformation.characterGender.text =
            bundleCharacter?.gender ?: bundleCharacterEntity!!.gender
        bindingInformation.characterSpecie.text =
            bundleCharacter?.species ?: bundleCharacterEntity!!.species
        bindingInformation.characterStatus.text =
            bundleCharacter?.status?.uppercase() ?: bundleCharacterEntity!!.status.uppercase()
    }
}