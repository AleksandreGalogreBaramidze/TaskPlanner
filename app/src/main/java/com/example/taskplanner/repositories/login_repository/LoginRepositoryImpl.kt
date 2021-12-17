package com.example.taskplanner.repositories.login_repository

import com.example.taskplanner.repositories.login_repository.LoginRepository
import com.example.taskplanner.utils.Resource
import com.example.taskplanner.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler
) : LoginRepository {
    override suspend fun logIn(email: String, password: String): Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                responseHandler.handleSuccess(result)
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }
}