package com.nuzul.cleantestapp.authentication.presentation.components

import com.nuzul.cleantestapp.authentication.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)