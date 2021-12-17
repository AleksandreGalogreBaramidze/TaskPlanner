package com.example.taskplanner.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.models.ProjectModel
import com.example.taskplanner.models.TaskModel
import com.example.taskplanner.repositories.projects_detail_repository.SetProjectDetailsRepositoryImplementation
import com.example.taskplanner.repositories.create_task_repository.CreateTaskRepositoryImpl
import com.example.taskplanner.repositories.set_task_repository.SetTaskRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val tasksRepository: SetTaskRepositoryImpl,
    private val createSubTaskRepository: CreateTaskRepositoryImpl,
    private val taskDetailsRepository: SetProjectDetailsRepositoryImplementation
) : ViewModel() {
    private var _tasksListLiveData = MutableLiveData<List<TaskModel>>()
    val tasksListLiveData: LiveData<List<TaskModel>> = _tasksListLiveData

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> = _endDate

    fun dataProvider(day: Int, month: Int, year: Int) {
        _endDate.postValue("$day-${month + 1}-$year")
    }

    private val _projectDetails = MutableLiveData<ProjectModel>()
    val projectLiveData: LiveData<ProjectModel> = _projectDetails

    fun createTask(taskName: String, projectUid: String, startDate: String, EndDate: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                createSubTaskRepository.createTask(taskName, projectUid, startDate, EndDate)
            }
        }
    }

    fun dataValidator(startDate: String, endDate: String): Boolean {
        var result = false
        val startDateDay: Int = startDate.substring(0..1).toInt()
        val startDateMonth: Int = startDate.substring(3..4).toInt()
        val startDateYear: Int = startDate.substring(6..9).toInt()
        val endDateDay: Int = endDate.substring(0..1).toInt()
        val endDateMonth: Int = endDate.substring(3..4).toInt()
        val endDateYear: Int = endDate.substring(6..9).toInt()
        if (startDateYear < endDateYear) {
            result = true
        } else if (startDateYear == endDateYear) {
            if (startDateMonth < endDateMonth) {
                result = true
            } else {
                if (startDateDay < endDateDay) {
                    result = true
                }
            }
        }
        return result
    }

    fun updateProgress(projectUid: String, progress: String) {
        viewModelScope.launch {
            taskDetailsRepository.updateProgress(projectUid, progress)
        }
    }

    fun updateProject(projectUid: String, projectName: String, projectDescription: String) {
        viewModelScope.launch {
            taskDetailsRepository.updateProject(projectUid, projectName, projectDescription)
        }
    }

    fun deleteProjectById(projectUid: String) {
        viewModelScope.launch {
            taskDetailsRepository.deleteProject(projectUid)
        }
    }

    fun getProjectDetails(projectUid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _projectDetails.postValue(taskDetailsRepository.getProjects(projectUid))
            }
        }
    }

    fun getTasks(projectUid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tasksListLiveData.postValue(
                    tasksRepository.setTask(projectUid)
                )
            }
        }
    }
}