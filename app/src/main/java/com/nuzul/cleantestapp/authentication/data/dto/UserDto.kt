package com.nuzul.cleantestapp.authentication.data.dto

import com.nuzul.cleantestapp.authentication.domain.model.User

data class UserDto(
  val username: String,
  val password: String,
  val token: String,
)
fun UserDto.toUser(): User {
    return User(
        username = username,
        password = password,
        token = token,
    )
}