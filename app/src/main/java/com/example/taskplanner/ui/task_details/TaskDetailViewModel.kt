package com.example.taskplanner.ui.task_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.models.TaskModel
import com.example.taskplanner.repositories.task_details_repository.SetTaskDetailsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskDetailsRepositoryRepository: SetTaskDetailsRepositoryImpl,
): ViewModel() {
    private val _taskDetails = MutableLiveData<TaskModel>()
    val taskDetails: LiveData<TaskModel> = _taskDetails

    fun updateProgress (taskId: String,progress: String){
        viewModelScope.launch {
            taskDetailsRepositoryRepository.updateProgress(taskId,progress)
        }
    }

    fun deleteTaskById(taskId: String){
        viewModelScope.launch {
            taskDetailsRepositoryRepository.deleteTask(taskId)
        }
    }

    fun updateTask (projectUid: String, projectName: String){
        viewModelScope.launch {
            taskDetailsRepositoryRepository.updateProject(projectUid, projectName)
        }
    }

    fun getTasksDetails(projectUid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _taskDetails.postValue(taskDetailsRepositoryRepository.getProjects(projectUid))
            }
        }
    }
}