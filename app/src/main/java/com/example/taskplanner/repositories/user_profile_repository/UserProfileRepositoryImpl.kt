package com.example.taskplanner.repositories.user_profile_repository

import com.example.taskplanner.models.UserModel
import com.example.taskplanner.utils.Constants.JOB
import com.example.taskplanner.utils.Constants.USER_COLLECTION_NAME
import com.example.taskplanner.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserProfileRepository {
    private val usersCollection = fireStore.collection(USER_COLLECTION_NAME)
    private val uid = auth.currentUser!!.uid
    override suspend fun getUser(): Resource<UserModel> = withContext(Dispatchers.IO) {
        return@withContext try {
            val currentUser = usersCollection.document(uid).get().await().toObject<UserModel>()!!
            Resource.Success(currentUser)
        } catch (e: Exception) {
            Resource.Error(e.toString())
        }
    }

    override suspend fun addJob(job: String){
        usersCollection.document(uid).update(JOB, job)
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}