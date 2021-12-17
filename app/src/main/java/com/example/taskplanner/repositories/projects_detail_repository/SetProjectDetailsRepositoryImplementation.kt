package com.example.taskplanner.repositories.projects_detail_repository

import com.example.taskplanner.models.ProjectModel
import com.example.taskplanner.repositories.projects_detail_repository.SetProjectDetailsRepository
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.DESCRIPTION_KEY
import com.example.taskplanner.utils.Constants.PROGRESS_KEY
import com.example.taskplanner.utils.Constants.PROJECT_NAME_KEY
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SetProjectDetailsRepositoryImplementation @Inject constructor(
    fireStore: FirebaseFirestore,
) : SetProjectDetailsRepository {
    private val projects = fireStore.collection(Constants.PROJECTS_COLLECTION_NAME)
    override suspend fun getProjects(projectUid: String): ProjectModel {
        return projects.document(projectUid).get().await().toObject<ProjectModel>()!!
    }

    override suspend fun updateProgress(projectUid: String, progress: String) {
        projects.document(projectUid).update(PROGRESS_KEY, progress)
    }

    override suspend fun updateProject(
        projectUid: String,
        projectTitle: String,
        projectDescription: String
    ) {
        projects.document(projectUid).update(PROJECT_NAME_KEY, projectTitle)
        projects.document(projectUid).update(DESCRIPTION_KEY, projectDescription)
    }

    override suspend fun deleteProject(projectUid: String) {
        projects.document(projectUid).delete()
    }
}