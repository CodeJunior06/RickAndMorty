package com.codejunior.rickandmorty.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.codejunior.rickandmorty.R
import com.codejunior.rickandmorty.databinding.FragmentInformationDialogBinding
import com.codejunior.rickandmorty.view.OnExit

/**
 * Fragment Information
 */
class InformationDialog(private val callback: OnExit) : DialogFragment() {

    private lateinit var dialogBinding: FragmentInformationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialogBinding = FragmentInformationDialogBinding.inflate(layoutInflater)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.color.transparent
                )
            )
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
        }
        return dialogBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialogBinding.buttonExit.setOnClickListener {
            callback.exitApplication()
            requireDialog().dismiss()
        }

        dialogBinding.textButtonConnect.setOnClickListener {
            callback.tryConnection()
            requireDialog().dismiss()
        }
    }
}