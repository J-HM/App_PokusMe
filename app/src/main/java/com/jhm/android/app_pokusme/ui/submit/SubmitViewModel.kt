package com.jhm.android.app_pokusme.ui.submit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SubmitViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is submit Fragment"
    }
    val text: LiveData<String> = _text
}