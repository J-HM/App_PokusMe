package com.jhm.android.pokusme.data

import com.google.firebase.Timestamp

data class VoteData(
    var title: String = "제목",
    var content: String = "세부내용",
    var uploadTime: Timestamp?,
    var userId: String?
)