package com.example.taskplanner.repositories.task_details_repository

import com.example.taskplanner.models.TaskModel

interface SetTaskDetailsRepository {
    suspend fun getProjects(taskId: String): TaskModel
    suspend fun updateProgress(taskId: String, progress: String)
    suspend fun updateProject(taskId: String, taskName: String)
    suspend fun deleteTask(taskId: String)
}