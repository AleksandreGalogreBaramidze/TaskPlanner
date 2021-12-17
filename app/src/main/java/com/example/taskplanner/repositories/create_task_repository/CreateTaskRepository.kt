package com.example.taskplanner.repositories.create_task_repository

interface CreateTaskRepository {
    suspend fun createTask(taskName: String, projectId: String, startDate: String, endDate: String)
}