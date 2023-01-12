package com.codejunior.rickandmorty.extension

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codejunior.rickandmorty.view.MainView

fun Fragment.toastMessage(message: String) =
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

fun Activity.intentToMainView() = startActivity(Intent(this, MainView::class.java))
