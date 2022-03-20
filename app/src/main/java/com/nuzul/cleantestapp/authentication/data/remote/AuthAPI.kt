package com.nuzul.cleantestapp.authentication.data.remote

import okhttp3.RequestBody
import retrofit2.http.*

interface AuthAPI {

    @Multipart
    @POST("auth/login")
    suspend fun requestLogin(
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody
    ) : Map<String, Any>

    @Multipart
    @POST("register")
    suspend fun requestRegister(
        @Part("email") username: RequestBody,
        @Part("password") password: RequestBody
    ) : Map<String, Any>

}