package com.example.taskplanner.repositories.projects_detail_repository

import com.example.taskplanner.models.ProjectModel

interface SetProjectDetailsRepository {
    suspend fun getProjects(projectUid: String): ProjectModel
    suspend fun updateProgress(projectUid: String, progress: String)
    suspend fun updateProject(projectUid: String, projectTitle: String, projectDescription: String)
    suspend fun deleteProject(projectUid: String)
}