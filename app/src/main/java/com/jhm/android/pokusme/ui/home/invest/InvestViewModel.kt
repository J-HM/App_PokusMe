package com.jhm.android.pokusme.ui.home.invest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InvestViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is popular Fragment"
    }
    val text: LiveData<String> = _text
}