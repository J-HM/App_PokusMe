package com.jhm.android.pokusme.data

import com.google.firebase.Timestamp

data class VoteData(
    var title: String?,
    var content: String?,
    var uploadTime: Timestamp?,
    var userId: String?,
    var displayName: String?,
    var good: Number?,
    var bad: Number?,
    var comments: ArrayList<CommentData>? = null
)
