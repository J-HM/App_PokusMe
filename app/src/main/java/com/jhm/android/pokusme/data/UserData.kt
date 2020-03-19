package com.jhm.android.pokusme.data

data class UserData(
    var displayName: String = "이름",
    var email: String?,
    var isEmailVerified: Boolean?,
    var uid: String?
)