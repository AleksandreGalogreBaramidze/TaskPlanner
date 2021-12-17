package com.example.taskplanner.user_preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.taskplanner.utils.Constants.CURRENT_USER
import com.example.taskplanner.utils.Constants.CURRENT_USER_EMAIL_KEY
import com.example.taskplanner.utils.Constants.CURRENT_USER_PASSWORD_KEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE)
    }

    fun saveUser(userPassword: String, userEmail: String){
        sharedPreference.edit().putString(CURRENT_USER_PASSWORD_KEY, userPassword).apply()
        sharedPreference.edit().putString(CURRENT_USER_EMAIL_KEY, userEmail).apply()
    }

    fun getCurrentUserPassword() = sharedPreference.getString(CURRENT_USER_PASSWORD_KEY,DEFAULT_CURRENT_USER_PASSWORD)

    fun getCurrentUserEmail() = sharedPreference.getString(CURRENT_USER_EMAIL_KEY,DEFAULT_CURRENT_USER_EMAIL)

    fun currentUser(isActive: Boolean){
        sharedPreference.edit().putBoolean(CURRENT_USER, isActive).apply()
    }

    fun getCurrentUser() = sharedPreference.getBoolean(CURRENT_USER, DEFAULT_CURRENT_USER)

    companion object {
        private const val PREFERENCE_TAG = "current_user_email_and_password"
        private const val DEFAULT_CURRENT_USER = false
        private const val DEFAULT_CURRENT_USER_PASSWORD = ""
        private const val DEFAULT_CURRENT_USER_EMAIL = ""
    }
}