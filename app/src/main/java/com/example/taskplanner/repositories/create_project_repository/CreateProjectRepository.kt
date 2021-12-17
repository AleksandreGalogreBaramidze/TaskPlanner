package com.example.taskplanner.repositories.create_project_repository

interface CreateProjectRepository {
    suspend fun createProject(projectName: String, progress: String, description: String, startDate: String, endDate: String)
}