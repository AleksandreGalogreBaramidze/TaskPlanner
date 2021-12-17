package com.example.taskplanner.repositories.registration_repository


import android.net.Uri
import com.example.taskplanner.utils.Resource
import com.google.firebase.auth.AuthResult

interface RegistrationRepository {
    suspend fun register(email: String, password: String, userName: String, imageUri: Uri): Resource<AuthResult>
}