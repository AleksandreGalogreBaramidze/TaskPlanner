package com.example.taskplanner.repositories.task_details_repository

import com.example.taskplanner.models.TaskModel
import com.example.taskplanner.repositories.task_details_repository.SetTaskDetailsRepository
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.PROGRESS_KEY
import com.example.taskplanner.utils.Constants.TASK_NAME
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetTaskDetailsRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
): SetTaskDetailsRepository {
    private val task = fireStore.collection(Constants.TASKS_COLLECTION_NAME)
    override suspend fun getProjects(taskId: String): TaskModel {
        return task.document(taskId).get().await().toObject<TaskModel>()!!
    }
    override suspend fun updateProgress(taskId: String, progress: String) {
        task.document(taskId).update(PROGRESS_KEY, progress)
    }
    override suspend fun updateProject(
        taskId: String,
        taskName: String
    ) {
        task.document(taskId).update(TASK_NAME, taskName)
    }
    override suspend fun deleteTask(taskId: String) {
        task.document(taskId).delete()
    }
}