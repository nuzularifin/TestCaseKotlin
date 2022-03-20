package com.nuzul.cleantestapp.authentication.domain.repository

import com.nuzul.cleantestapp.authentication.data.dto.UserDto

interface AuthRepository {
    suspend fun requestRegister(email: String, password: String): Map<String, Any>

    suspend fun requestLogin(email: String, password: String): Map<String, Any>

}