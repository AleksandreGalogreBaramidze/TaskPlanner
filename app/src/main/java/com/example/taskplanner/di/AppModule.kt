package com.example.taskplanner.di

import com.example.taskplanner.repositories.projects_detail_repository.SetProjectDetailsRepository
import com.example.taskplanner.repositories.projects_detail_repository.SetProjectDetailsRepositoryImplementation
import com.example.taskplanner.repositories.task_details_repository.SetTaskDetailsRepositoryImpl
import com.example.taskplanner.repositories.task_details_repository.SetTaskDetailsRepository
import com.example.taskplanner.repositories.create_project_repository.CreateProjectRepository
import com.example.taskplanner.repositories.create_project_repository.CreateProjectRepositoryImpl
import com.example.taskplanner.repositories.login_repository.LoginRepository
import com.example.taskplanner.repositories.login_repository.LoginRepositoryImpl
import com.example.taskplanner.repositories.registration_repository.RegistrationRepository
import com.example.taskplanner.repositories.registration_repository.RegistrationRepositoryImpl
import com.example.taskplanner.repositories.set_project_repository.SetProjectListRepository
import com.example.taskplanner.repositories.set_project_repository.SetProjectListListRepositoryImpl
import com.example.taskplanner.repositories.set_task_repository.SetTaskRepositoryImpl
import com.example.taskplanner.repositories.set_task_repository.SetTasksRepository
import com.example.taskplanner.repositories.user_profile_repository.UserProfileRepository
import com.example.taskplanner.repositories.user_profile_repository.UserProfileRepositoryImpl
import com.example.taskplanner.utils.ResponseHandler

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRegistrationRepository(
        auth: FirebaseAuth,
        storage: FirebaseStorage,
        fireStore: FirebaseFirestore,
        handler: ResponseHandler
    ): RegistrationRepository = RegistrationRepositoryImpl(auth, storage, fireStore, handler)

    @Provides
    @Singleton
    fun provideLogInRepository(
        auth: FirebaseAuth,
        handler: ResponseHandler
    ): LoginRepository = LoginRepositoryImpl(auth, handler)

    @Provides
    @Singleton
    fun provideProfileRepository(
        fireStore: FirebaseFirestore,
        auth: FirebaseAuth,
    ): UserProfileRepository = UserProfileRepositoryImpl(fireStore, auth)

    @Provides
    @Singleton
    fun provideCreateProjectRepository(
        auth: FirebaseAuth,
        fireStore: FirebaseFirestore
    ): CreateProjectRepository = CreateProjectRepositoryImpl(auth, fireStore)

    @Provides
    @Singleton
    fun provideSetProjectRepository(
        fireStore: FirebaseFirestore,
        auth: FirebaseAuth,
    ): SetProjectListRepository = SetProjectListListRepositoryImpl(fireStore, auth)


    @Provides
    @Singleton
    fun provideProjectDetailsRepository(
        fireStore: FirebaseFirestore
    ): SetProjectDetailsRepository = SetProjectDetailsRepositoryImplementation(fireStore)

    @Provides
    @Singleton
    fun provideTaskDetailsRepository(
        fireStore: FirebaseFirestore
    ): SetTaskDetailsRepository = SetTaskDetailsRepositoryImpl(fireStore)

    @Provides
    @Singleton
    fun provideSetTasksRepository(
        fireStore: FirebaseFirestore
    ): SetTasksRepository = SetTaskRepositoryImpl(fireStore)

    @Provides
    @Singleton
    fun providesResponseHandler() = ResponseHandler()

}