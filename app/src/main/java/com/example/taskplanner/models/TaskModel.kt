package com.example.taskplanner.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class TaskModel (
   val projectId: String? = "",
   val taskId: String? = "",
   val taskName: String? = "",
   val progress: String? = "",
   val startDate: String = "",
   val endDate: String = "",
)