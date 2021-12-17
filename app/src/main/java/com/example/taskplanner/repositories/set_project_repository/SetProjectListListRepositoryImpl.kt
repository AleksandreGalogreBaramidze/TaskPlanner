package com.example.taskplanner.repositories.set_project_repository


import com.example.taskplanner.models.ProgressModel
import com.example.taskplanner.models.ProjectModel
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.DESCRIPTION_KEY
import com.example.taskplanner.utils.Constants.DONE
import com.example.taskplanner.utils.Constants.END_DATE_KEY
import com.example.taskplanner.utils.Constants.IN_PROGRESS
import com.example.taskplanner.utils.Constants.OWNER_ID_KEY
import com.example.taskplanner.utils.Constants.PROGRESS_KEY
import com.example.taskplanner.utils.Constants.PROJECT_ID_KEY
import com.example.taskplanner.utils.Constants.START_DATE_KEY
import com.example.taskplanner.utils.Constants.PROJECT_NAME_KEY
import com.example.taskplanner.utils.Constants.TODO_PROGRESS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetProjectListListRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : SetProjectListRepository {
    private val projects = fireStore.collection(Constants.PROJECTS_COLLECTION_NAME)
    override suspend fun getProjects(): List<ProjectModel> {
        val uid = auth.currentUser!!.uid
        val list = mutableListOf<ProjectModel>()
        val resultProjects = projects.whereEqualTo("userUid", uid).get().await().documents
        resultProjects.forEach { doc ->
            list.add(
                ProjectModel(
                    projectName = doc[PROJECT_NAME_KEY] as String?,
                    userUid = doc[OWNER_ID_KEY] as String?,
                    projectUid = doc[PROJECT_ID_KEY] as String,
                    description = doc[DESCRIPTION_KEY] as String?,
                    startDate = doc[START_DATE_KEY] as String?,
                    endDate = doc[END_DATE_KEY] as String?,
                    progress = doc[PROGRESS_KEY] as String?,
                    subTasks = emptyList()
                )
            )
        }
        return list
    }

    override suspend fun countProgress(): ProgressModel {
        val uid = auth.currentUser!!.uid
        val todoResultNumber = projects.whereEqualTo("userUid", uid).whereEqualTo("progress", TODO_PROGRESS).get().await().documents.size
        val inProgressNumber = projects.whereEqualTo("userUid", uid).whereEqualTo("progress", IN_PROGRESS).get().await().documents.size
        val doneNumber = projects.whereEqualTo("userUid", uid).whereEqualTo("progress", DONE).get().await().documents.size
        return ProgressModel(todoResultNumber, inProgressNumber, doneNumber)
    }
}