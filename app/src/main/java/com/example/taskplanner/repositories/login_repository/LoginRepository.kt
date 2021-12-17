package com.example.taskplanner.repositories.login_repository

import com.example.taskplanner.utils.Resource
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun logIn(email: String, password: String): Resource<AuthResult>
}