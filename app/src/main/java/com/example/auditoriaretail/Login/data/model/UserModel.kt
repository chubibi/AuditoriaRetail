package com.example.auditoriaretail.Login.data.model

import java.io.Serializable

data class UserModel(
    val fullName: String,
    var email: String,
    var password: String,
    var phone: String,
    var singIn: Int = -1,
    val uuid: String = ""
) : Serializable
