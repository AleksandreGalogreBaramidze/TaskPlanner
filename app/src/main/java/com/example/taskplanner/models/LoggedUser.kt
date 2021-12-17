package com.example.taskplanner.models

data class LoggedUser(
    val isLogged: Boolean,
    val loggedUserEmail: String?,
    val loggedUserPassword: String?
)