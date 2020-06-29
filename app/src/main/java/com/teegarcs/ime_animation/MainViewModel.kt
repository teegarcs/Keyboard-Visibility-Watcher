package com.teegarcs.ime_animation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _keyboardActive = MutableLiveData<Int>()
    val keyboardActive: LiveData<Int> = _keyboardActive


    //callback to be provided to extension function for keyboard changes
    val keyboardCallback: (visible: Boolean) -> Unit = {
        _keyboardActive.value = if (it) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}