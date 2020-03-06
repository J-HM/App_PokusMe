package com.jhm.android.pokusme.ui.submit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SubmitViewModel : ViewModel() {
    val displayName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val isEmailVerified = MutableLiveData<Boolean>()
}