package com.example.taskplanner.repositories.create_project_repository

import android.util.Log.d
import com.example.taskplanner.models.ProjectModel
import com.example.taskplanner.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class CreateProjectRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    fireStore: FirebaseFirestore,
) : CreateProjectRepository {
    private val projects = fireStore.collection(Constants.PROJECTS_COLLECTION_NAME)
    private val projectUid = UUID.randomUUID().toString()
    override suspend fun createProject(
        projectName: String,
        progress: String,
        description: String,
        startDate: String,
        endDate: String
    ) {
        withContext(Dispatchers.IO){
            try {
                val uid = auth.currentUser!!.uid
                val project = ProjectModel(
                    projectName,
                    uid,
                    projectUid,
                    progress,
                    description,
                    startDate,
                    endDate
                )
                projects.document(projectUid).set(project).await()
            }
            catch (e: Exception){
                d("result", e.toString())
            }
        }
    }
}