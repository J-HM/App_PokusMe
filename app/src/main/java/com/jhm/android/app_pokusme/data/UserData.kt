package com.jhm.android.app_pokusme.data

data class UserData(
    var displayName: String = "이름없음",
    var email: String?,
    var isEmailVerified: Boolean?
)