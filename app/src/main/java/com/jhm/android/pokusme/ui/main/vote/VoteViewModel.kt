package com.jhm.android.pokusme.ui.main.vote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is vote Fragment"
    }
    val text: LiveData<String> = _text
}