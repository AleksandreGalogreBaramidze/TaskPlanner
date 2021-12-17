package com.example.taskplanner.repositories.create_task_repository

import com.example.taskplanner.models.TaskModel
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.TODO_PROGRESS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class CreateTaskRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : CreateTaskRepository {
    private val taskUid = UUID.randomUUID().toString()
    private val task = fireStore.collection(Constants.TASKS_COLLECTION_NAME)
    override suspend fun createTask(
        taskName: String,
        projectId: String,
        startDate: String,
        endDate: String
    ) {
        val tasks = TaskModel(
            taskName = taskName,
            progress = TODO_PROGRESS,
            projectId = projectId,
            startDate = startDate,
            endDate = endDate,
            taskId = taskUid
        )
        task.document(taskUid).set(tasks).await()
    }
}