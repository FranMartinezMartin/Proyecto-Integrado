package com.example.pi_v1.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is maps Fragment"
    }
    val text: LiveData<String> = _text
}