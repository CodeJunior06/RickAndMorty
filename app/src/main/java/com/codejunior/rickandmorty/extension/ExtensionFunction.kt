package com.codejunior.rickandmorty.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toastMessage(message:String){
    Toast.makeText(requireContext(), message,Toast.LENGTH_LONG).show()
}