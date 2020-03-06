package com.jhm.android.pokusme.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    val displayName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val isEmailVerified = MutableLiveData<Boolean>()
}