package com.example.taskplanner.utils

import com.google.firebase.auth.*

class ResponseHandler {
    fun <T> handleException(e: Exception): Resource<T> {
        return when (e) {
            is FirebaseAuthInvalidUserException -> Resource.Error(INVALID_USER_ERROR)
            is FirebaseAuthInvalidCredentialsException -> Resource.Error(NOT_VALID_MAIL_ERROR)
            is FirebaseAuthWeakPasswordException -> Resource.Error(PASSWORD_ERROR)
            is FirebaseAuthUserCollisionException -> Resource.Error(ALREADY_USED_MAIL_ERROR)
            else -> Resource.Error(UNKNOWN_ERROR)
        }
    }
    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    companion object{
        const val UNKNOWN_ERROR = "ამოუცნობი შეცდომა"
        const val ALREADY_USED_MAIL_ERROR = "იმეილი უკვე გამოყენებულია"
        const val PASSWORD_ERROR = "პაროლი 6 სიმბოლოზე ნაკლებს არ უნდა შეადგენდეს"
        const val NOT_VALID_MAIL_ERROR = "იმეილი არავალიდურია"
        const val INVALID_USER_ERROR = "ინფორმაცია არასწორია ან იუზერი წაშლილია"
    }
}