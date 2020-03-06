package com.jhm.android.pokusme.ui.drawer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {
    val displayName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
}