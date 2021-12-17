package com.example.taskplanner.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ProjectModel(
    val projectName: String? = "",
    val userUid: String?  = "",
    val projectUid: String = "",
    val progress: String? = "",
    val description: String? = "",
    val startDate: String? = "",
    val endDate: String? = "",
    @get:Exclude var subTasks: List<TaskModel>? = null
)