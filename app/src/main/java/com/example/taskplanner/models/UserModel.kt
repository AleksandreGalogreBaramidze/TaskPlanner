package com.example.taskplanner.models

import com.example.taskplanner.utils.Constants.DEFAULT_PROFILE_IMAGE_URL

data class UserModel(
    val uid: String = "",
    val userName: String = "",
    val job: String = "",
    val profileImageUrl: String = DEFAULT_PROFILE_IMAGE_URL,
)