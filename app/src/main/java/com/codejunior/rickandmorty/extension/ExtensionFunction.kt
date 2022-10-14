package com.codejunior.rickandmorty.extension

import android.app.Activity
import android.widget.Toast

fun Activity.toastMessage(message:String){
    Toast.makeText(this, message,Toast.LENGTH_LONG).show()
}