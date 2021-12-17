package com.example.taskplanner.repositories.user_profile_repository

import com.example.taskplanner.models.UserModel
import com.example.taskplanner.utils.Resource

interface UserProfileRepository {
    suspend fun getUser(): Resource<UserModel>
    suspend fun addJob(job: String)
    suspend fun signOut()
}