package com.example.taskplanner.repositories.set_project_repository

import com.example.taskplanner.models.ProgressModel
import com.example.taskplanner.models.ProjectModel

interface SetProjectListRepository {
    suspend fun getProjects(): List<ProjectModel>
    suspend fun countProgress(): ProgressModel
}