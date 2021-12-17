package com.example.taskplanner.ui.registration

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.repositories.registration_repository.RegistrationRepositoryImpl
import com.example.taskplanner.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegistrationRepositoryImpl) :
    ViewModel() {
    private val _signUpResponse  = MutableLiveData<Resource<AuthResult>>()
    val signUpResponse: LiveData<Resource<AuthResult>> = _signUpResponse

    fun signUp(
        email: String,
        password: String,
        userName: String,
        imageUri: Uri
    ) =
        viewModelScope.launch {
            _signUpResponse.postValue(Resource.Loading())
            withContext(Dispatchers.IO) {
                _signUpResponse.postValue(
                    repository.register(
                        email,
                        password,
                        userName,
                        imageUri
                    )
                )
            }
        }
}