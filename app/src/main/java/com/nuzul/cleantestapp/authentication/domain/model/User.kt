package com.nuzul.cleantestapp.authentication.domain.model

data class User(
    var username: String,
    var password: String,
    var token: String,
) {
//    fun fromJson(json: Map<String, Any?>): User {
//        return User(
//        username = json["email"].toString(),
//        password = json["password"].toString(),
//        token = json["token"].toString()
//        )
//    }
}
