package com.jhm.android.pokusme.data

import com.google.firebase.Timestamp

data class VoteData(
    var title: String = "...",
    var content: String = "...",
    var uploadTime: Timestamp? = null,

    var userId: String = "...",
    var voteId: String = "...",

    var commentCount: Number = 0,
    var goodCount: Number = 0,
    var badCount: Number = 0,

    var displayName: String = "..."
)
