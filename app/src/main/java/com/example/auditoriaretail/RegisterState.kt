package com.example.auditoriaretail

sealed class State {
    object Loading : State()
    object Successful : State()
    data class Error(val message: String) : State()
}

sealed class Result<T>
class SuccessResult<T>(val result: T) : Result<T>()
class Loading<T> : Result<T>()
data class Error<T>(val message: String) : Result<T>()
