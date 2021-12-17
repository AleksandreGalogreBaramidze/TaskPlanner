package com.example.taskplanner.repositories.registration_repository

import android.net.Uri
import com.example.taskplanner.models.UserModel
import com.example.taskplanner.utils.Constants.USER_COLLECTION_NAME
import com.example.taskplanner.utils.Resource
import com.example.taskplanner.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    fireStore: FirebaseFirestore,
    private val responseHandler: ResponseHandler
) : RegistrationRepository {
    private val users = fireStore.collection(USER_COLLECTION_NAME)
    override suspend fun register(email: String, password: String, userName: String, imageUri: Uri): Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid!!
                val uploadImage = storage.getReference(uid).putFile(imageUri).await()
                val imageUrl = uploadImage.metadata?.reference?.downloadUrl?.await().toString()
                val user = UserModel(
                    uid,
                    userName,
                    "unemployed",
                    profileImageUrl = imageUrl,
                )
                users.document(uid).set(user).await()
                responseHandler.handleSuccess(result)
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }


}