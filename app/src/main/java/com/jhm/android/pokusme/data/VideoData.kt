package com.jhm.android.pokusme.data

import android.net.Uri

data class VideoData(
    val title: String = "제목없음",
    val videoUri: Uri?,
    val thumbnailUri: Uri?,
    val description: String = "설명없음"
)