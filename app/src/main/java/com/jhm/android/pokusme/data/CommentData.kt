package com.jhm.android.pokusme.data

import com.google.firebase.Timestamp

data class CommentData (
    var content: String?,
    var uploadTime: Timestamp?,
    var userId: String?,
    var displayName: String?
)