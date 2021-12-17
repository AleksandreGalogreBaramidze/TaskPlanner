package com.example.taskplanner.ui.newproject

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.repositories.create_project_repository.CreateProjectRepository
import com.example.taskplanner.utils.Constants.TODO_PROGRESS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewProjectViewModel @Inject constructor(private val repository: CreateProjectRepository) :
    ViewModel() {

    private val _endDate = MutableLiveData<String>()
    val endDate: LiveData<String> = _endDate

    fun dataProvider(day: Int, month: Int, year: Int) {
        _endDate.postValue("$day-${month + 1}-$year")
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

    fun createProject(
        projectName: String,
        description: String,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.createProject(
                    projectName,
                    progress = TODO_PROGRESS,
                    description,
                    startDate,
                    endDate
                )
            }
        }

    }
}