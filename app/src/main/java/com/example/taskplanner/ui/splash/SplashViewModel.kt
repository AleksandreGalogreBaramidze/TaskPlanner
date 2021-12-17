package com.example.taskplanner.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.models.LoggedUser
import com.example.taskplanner.repositories.login_repository.LoginRepositoryImpl
import com.example.taskplanner.user_preferences.UserPreference
import com.example.taskplanner.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userPreference: UserPreference, private val repository: LoginRepositoryImpl): ViewModel() {
    private var _loggedUserInfo = MutableLiveData<LoggedUser>()
    val loggedUserInfo: LiveData<LoggedUser> = _loggedUserInfo

    private val _logInResponse = MutableLiveData<Resource<AuthResult>>()
    val logInResponse: LiveData<Resource<AuthResult>> = _logInResponse

    fun logIn(email: String?, password: String?) = viewModelScope.launch {
        _logInResponse.postValue(Resource.Loading())
        withContext(Dispatchers.IO) {
            _logInResponse.postValue(repository.logIn(email!!, password!!))
        }
    }

    fun setCurrentUserInfo(){
        _loggedUserInfo.postValue(LoggedUser(userPreference.getCurrentUser(),userPreference.getCurrentUserEmail(),userPreference.getCurrentUserPassword()))

    }
}