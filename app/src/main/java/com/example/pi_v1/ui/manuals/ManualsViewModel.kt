package com.example.pi_v1.ui.manuals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ManualsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is manual Fragment"
    }
    val text: LiveData<String> = _text
}