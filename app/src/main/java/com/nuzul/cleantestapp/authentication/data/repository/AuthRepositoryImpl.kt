package com.nuzul.cleantestapp.authentication.data.repository

import com.nuzul.cleantestapp.authentication.data.remote.AuthAPI
import com.nuzul.cleantestapp.authentication.domain.repository.AuthRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthAPI
): AuthRepository {

    override suspend fun requestRegister(email: String, password: String): Map<String, Any> {
        val userEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val userPass = password.toRequestBody("text/plain".toMediaTypeOrNull())
        return api.requestRegister(username = userEmail, password = userPass)
    }

    override suspend fun requestLogin(email: String, password: String): Map<String, Any> {
        val userEmail = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val userPass = password.toRequestBody("text/plain".toMediaTypeOrNull())
        return api.requestLogin(username = userEmail, password = userPass)
    }
}