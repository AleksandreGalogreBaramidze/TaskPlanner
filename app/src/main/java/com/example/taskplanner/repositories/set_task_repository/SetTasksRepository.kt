package com.example.taskplanner.repositories.set_task_repository
import com.example.taskplanner.models.TaskModel

interface SetTasksRepository {
    suspend fun setTask(projectUid: String): List<TaskModel>
}