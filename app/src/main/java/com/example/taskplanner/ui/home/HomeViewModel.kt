package com.example.taskplanner.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.models.ProgressModel
import com.example.taskplanner.models.ProjectModel
import com.example.taskplanner.models.UserModel
import com.example.taskplanner.repositories.set_project_repository.SetProjectListListRepositoryImpl
import com.example.taskplanner.repositories.user_profile_repository.UserProfileRepositoryImpl
import com.example.taskplanner.user_preferences.UserPreference
import com.example.taskplanner.utils.Constants.NO_USER_DEFAULT_SETTINGS
import com.example.taskplanner.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class  HomeViewModel @Inject constructor(
    private val userPreference: UserPreference,
    private val repository: UserProfileRepositoryImpl,
    private val tasksListRepository: SetProjectListListRepositoryImpl,
    ): ViewModel() {

    private val _user = MutableLiveData<Resource<UserModel>>()
    val user: LiveData<Resource<UserModel>> = _user

    private var _optionLiveData = MutableLiveData<List<ProjectModel>>()
    val optionLiveData: LiveData<List<ProjectModel>> = _optionLiveData

    private var _progressLiveData = MutableLiveData<ProgressModel>()
    val progressLiveData : LiveData<ProgressModel> = _progressLiveData

    fun getProgressStatistics(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _progressLiveData.postValue(
                    tasksListRepository.countProgress()
                )
            }
        }
    }

    fun getTasks(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _optionLiveData.postValue(
                    tasksListRepository.getProjects()
                )
            }
        }
    }
    //TODO handling signoutis
    fun signOutUser(){
        userPreference.saveUser(NO_USER_DEFAULT_SETTINGS, NO_USER_DEFAULT_SETTINGS)
        userPreference.currentUser(false)
        viewModelScope.launch {
            repository.signOut()
        }
    }

    fun updateJob (job: String){
        viewModelScope.launch {
            repository.addJob(job)
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        _user.postValue(Resource.Loading())
        withContext(Dispatchers.IO) {
            _user.postValue(repository.getUser())
        }
    }
}