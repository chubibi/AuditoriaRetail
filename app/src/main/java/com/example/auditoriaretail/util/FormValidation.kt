package com.example.auditoriaretail.util

import java.util.regex.Pattern

object FormValidation {

    fun validateName(name: String): Boolean {
        val regex = "^[a-zA-Z ]+$"
        return validateRegex(regex, name) && name.isNotBlank() && name.length > 10
    }

    fun validateEmail(email: String): Boolean {
        val regex =
            "^[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\$"
        return validateRegex(regex, email)
    }

    fun validatePassword(password: String): Boolean {
        val regex = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}\$"
        return validateRegex(regex, password)
    }

    fun validatePhone(password: String): Boolean {
        val regex = "^\\d{10}\$"
        return validateRegex(regex, password)
    }

    fun validateRegex(regex: String, value: String): Boolean {
        val pattern: Pattern = Pattern.compile(regex)
        return pattern.matcher(value).matches()
    }
}
