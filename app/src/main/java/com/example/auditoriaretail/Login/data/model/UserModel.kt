package com.example.auditoriaretail.Login.data.model

import java.io.Serializable

data class UserModel(
    var email: String,
    var password: String,
    var singIn: Int = -1,
    val uuid: String = ""
) : Serializable
