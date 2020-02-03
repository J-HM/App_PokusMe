package com.jhm.android.app_pokusme.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    
    private val _number = MutableLiveData<Int>().apply {
        value = 0
    }
    val number: LiveData<Int> = _number
}