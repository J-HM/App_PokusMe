package com.jhm.android.pokusme.ui.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileEditViewModel : ViewModel() {
    val displayName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val isEmailVerified = MutableLiveData<Boolean>()
}