package com.example.taskplanner.repositories.set_task_repository

import com.example.taskplanner.models.TaskModel
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.TASK_ID
import com.example.taskplanner.utils.Constants.TASK_NAME
import com.example.taskplanner.utils.Constants.TASK_PROGRESS
import com.example.taskplanner.utils.Constants.TASK_PROJECT_ID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SetTaskRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore
) : SetTasksRepository {
    private val task = fireStore.collection(Constants.TASKS_COLLECTION_NAME)
    override suspend fun setTask(projectUid: String): List<TaskModel> =
        withContext(Dispatchers.IO) {
            val list = mutableListOf<TaskModel>()
            val resultProjects = task.whereEqualTo(TASK_PROJECT_ID, projectUid).get().await().documents
            resultProjects.forEach { doc ->
                list.add(
                    TaskModel(
                        projectId = doc[TASK_PROJECT_ID] as String,
                        taskId = doc[TASK_ID] as String,
                        taskName = doc[TASK_NAME] as String,
                        progress = doc[TASK_PROGRESS] as String,
                    )
                )
            }
            return@withContext list
        }
}